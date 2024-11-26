import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;

import Game.Tabuleiro;
import Game.Peca.Peca;
import Game.Cor;
import Game.Partida;
import Game.Posicao;


import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.Calendar;
public class Main extends JPanel {
    private static final int TILE_SIZE = 100;
    private static final int BOARD_SIZE = 8;
    private static final long TURN_DURATION_SECONDS = 120;//5 seconds for testing 120 default
    
    private Partida partida;
    private Tabuleiro tabuleiro;
    private Image[][] pieceImages;
    private Cor currentPlayer = Cor.BRANCO;
    private Point selectedTile = null;
    private Point selectedPieceTile = null;
    private Point selectedMovePieceTile = null;
    private JLabel currentPlayerLabel;
    private Clip boardSound;
    private Color highlightColor = new Color(8, 200, 0, 128);
    private Color moveColor = new Color(255, 200, 0, 128);
    private JLabel currentTimerLabel;
    private long t=TURN_DURATION_SECONDS;
    public Main(Partida partida, JLabel currentPlayerLabel,JLabel currentTimerLabel) {
        //Timer turn
        Timer timer = new Timer();
        long delay_turn_ms = TURN_DURATION_SECONDS*1000; //sec * ms
        var c=Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND,(int)TURN_DURATION_SECONDS);
        Date now=c.getTime();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time is up!");
                try{
                    atualizar();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
            }
        };
        timer.scheduleAtFixedRate(task, now, delay_turn_ms);
        //Timer Update time lavel
        Timer timer2 = new Timer();
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                if(t>0){
                    t--;
                    updateCurrentTimerLabel();
                }
            }
        };
        timer2.scheduleAtFixedRate(task2, new Date(), 1000);
    
        
        

        setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        this.tabuleiro = partida.getTabuleiro();
        this.partida = partida;
        this.currentPlayerLabel = currentPlayerLabel;
        this.currentTimerLabel=currentTimerLabel;
        
        updateCurrentPlayerLabel();
        loadPieceImages();
        loadAudio();
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
                            // Does not work on my machine for some reason
                            if (boardSound != null && boardSound.isOpen()) {
                                if (boardSound.isRunning()) {
                                    boardSound.stop();
                                }
                                System.out.println(boardSound);
                                System.out.println("Playing sound");
                                boardSound.setFramePosition(0);
                                boardSound.start();
                            }
                            peca.movimentar(new Posicao(row, col),tabuleiro);
                            selectedPieceTile = null;
                            selectedMovePieceTile = null;
                            loadPieceImages();
                            //re-use this
                            atualizar();
                        }
                    } else{
                        selectedTile = null;
                        selectedPieceTile = null;
                        selectedMovePieceTile = null;
                    }
                    
                }
                repaint();
            }
        });
    }

    
    private void atualizar(){
        this.t=TURN_DURATION_SECONDS;
        try{
            partida.mudarTurno();
        } catch (CloneNotSupportedException ex){
            System.out.println("Erro ao mudar turno: "+ex.getMessage());
        }
        currentPlayer=partida.getJogadorAtual().getCor();
        updateCurrentPlayerLabel();
        if (partida.isFimDeJogo()) {
            JOptionPane.showMessageDialog(Main.this, "Fim de Jogo! " + partida.getVencedor().getCor() + " venceu! com "+ partida.getVencedor().getMovimentos()+" movimentos", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    
    private void updateCurrentPlayerLabel() {
        currentPlayerLabel.setText("Jogador Atual: " + (currentPlayer == Cor.BRANCO ? "Branco" : "Preto"));
    }

    private void updateCurrentTimerLabel() {
        currentTimerLabel.setText("Timer: "+t);
    }
 
    private void loadAudio(){
        try{
            InputStream audioSrc = getClass().getResourceAsStream("/Resources/audios/teste.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            boardSound = AudioSystem.getClip();
            boardSound.open(audioStream);
            System.out.println("Audio loaded");
            boardSound.setFramePosition(0);
        } catch (Exception e) {
            System.out.println("Error loading audio: " + e.getMessage());
        }

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
                    g.setColor(moveColor);
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    var pos=tabuleiro.calcularMovimentosValidos(tabuleiro.getPeca(new Posicao(row, col)), tabuleiro);
                    for (Posicao movimento : pos) {
                        if(currentPlayer==Cor.PRETO){
                            System.out.println("Movimento: "+movimento.getLinha() + " "+ movimento.getColuna());
                        }
                        g.setPaintMode();
                        g.setXORMode(Color.WHITE);//Only way to work on some tiles
                        g.setColor(highlightColor);
                        g.fillRect(movimento.getColuna() * TILE_SIZE, movimento.getLinha() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        System.out.println("Highlighting tile: (" + movimento.getLinha() + ", " + movimento.getColuna() + ")");
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
        partida.iniciarJogo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JLabel currentPlayerLabel = new JLabel("Jogador Atual: Branco");
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel currentTimerLabel = new JLabel("Timer: "+TURN_DURATION_SECONDS);
        currentTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, BOARD_SIZE * TILE_SIZE));
        sidebar.add(currentPlayerLabel, BorderLayout.CENTER);
        sidebar.add(currentTimerLabel, BorderLayout.PAGE_END);
        Main boardPanel = new Main(partida,currentPlayerLabel,currentTimerLabel);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(sidebar, BorderLayout.EAST);
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
