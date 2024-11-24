import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import Game.Tabuleiro;
import Game.Peca.Peca;
import Game.Cor;
import Game.Partida;
import Game.Posicao;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;
public class Main extends JPanel {
    private static final int TILE_SIZE = 100;
    private static final int BOARD_SIZE = 8;
    private Partida partida;
    private Tabuleiro tabuleiro;
    private Image[][] pieceImages;
    private Cor currentPlayer = Cor.BRANCO;
    private Point selectedTile = null;
    private Point selectedPieceTile = null;
    private Point selectedMovePieceTile = null;

    public Main(Partida partida,Tabuleiro tabuleiro) {
        setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        this.tabuleiro = tabuleiro;
        this.partida = partida;
        loadPieceImages();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;

                if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) {
                    selectedTile = new Point(col, row);
                    Peca piece = tabuleiro.getTabuleiro()[row][col];
                    if (piece != null && piece.getCor().equals(currentPlayer)) {
                        System.out.println("Selected piece at (" + row + ", " + col + "): " + piece);
                        selectedPieceTile = new Point(col, row);
                        selectedTile=null;
                    }  else if (selectedPieceTile != null) {
                        selectedMovePieceTile = new Point(col, row);
                        System.out.println("Selected move at (" + row + ", " + col + ")");
                        var peca=tabuleiro.getPeca(new Posicao(selectedPieceTile.y, selectedPieceTile.x));
                        if(peca.movimentoValido(new Posicao(row, col), tabuleiro)){
                            peca.movimentar(new Posicao(row, col),tabuleiro);
                            selectedPieceTile = null;
                            selectedMovePieceTile = null;
                            loadPieceImages();
                            try{
                                partida.mudarTurno();
                            } catch (CloneNotSupportedException ex){
                                System.out.println("Erro ao mudar turno");
                            }
                            currentPlayer=partida.getJogadorAtual().getCor();
                            if (partida.isFimDeJogo()) {
                                JOptionPane.showMessageDialog(Main.this, 
                                "Fim de Jogo! " + partida.getVencedor().getCor() + " venceu! com "+ partida.getVencedor().getMovimentos()+" movimentos", 
                                "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
                                System.exit(0);
                            }
                        }
                    }
                    repaint();
                }
            }
        });
    }

 
    private void loadPieceImages() {
        pieceImages = new Image[BOARD_SIZE][BOARD_SIZE];
        for (Peca[] pecas : this.tabuleiro.getTabuleiro()) {
            for (Peca peca : pecas) {
                if (peca != null) {
                    try {
                        URL url = getClass().getResource(peca.getSpritePath());
                        if (url != null) {
                            Image image = Toolkit.getDefaultToolkit().getImage(url);
                            pieceImages[peca.getPosicao().getLinha()][peca.getPosicao().getColuna()] = image;
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + peca.getSpritePath());
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                if (selectedPieceTile != null && selectedPieceTile.x == col && selectedPieceTile.y == row) {
                    g.setColor(new Color(255, 200, 0, 128));
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    var pos=tabuleiro.calcularMovimentosValidos(tabuleiro.getPeca(new Posicao(row, col)), tabuleiro);
                    for (Posicao movimento : pos) {
                        g.setColor(new Color(8, 200, 0 ,128));
                        g.fillRect(movimento.getColuna() * TILE_SIZE, movimento.getLinha() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        System.out.println("Highlighting tile: (" + row + ", " + col + ")");
                    }
                }

                if (pieceImages[row][col] != null) {
                    g.drawImage(pieceImages[row][col], col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SoloChessing");
        Partida partida = new Partida();
        partida.iniciarJogo(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        Main boardPanel = new Main(partida,partida.getTabuleiro());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        frame.setFocusable(true);
    }
}
