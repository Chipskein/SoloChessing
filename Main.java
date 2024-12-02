import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import Game.Tabuleiro;
import Game.Peca.Peca;
import Game.Peca.Peao;
import Game.Cor;
import Game.Partida;
import Game.Posicao;

public class Main extends JPanel {
    private static final int TILE_SIZE = 100;
    private static final int BOARD_SIZE = 8;
    private static final long TURN_DURATION_SECONDS = 120; // Default 120s
    private static final Color HIGHLIGHT_COLOR = new Color(8, 200, 0, 128);
    private static final Color MOVE_COLOR = new Color(255, 200, 0, 128);

    private final Partida partida;
    private final Tabuleiro tabuleiro;
    private final JLabel currentPlayerLabel;
    private final JLabel currentTimerLabel;
    private final JFrame frame;

    private Image[][] pieceImages;
    private Clip boardSound;
    private Cor currentPlayer;
    private Point selectedPieceTile = null;
    private Posicao promotedPiecePos = null;
    private boolean show = false;
    private long t = TURN_DURATION_SECONDS;

    public Main(Partida partida, JLabel currentPlayerLabel, JLabel currentTimerLabel, JFrame frame) {
        this.partida = partida;
        this.tabuleiro = partida.getTabuleiro();
        this.currentPlayerLabel = currentPlayerLabel;
        this.currentTimerLabel = currentTimerLabel;
        this.frame = frame;
        
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
        updateCurrentPlayerLabel();
        loadPieceImages();
        loadAudio();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;
                if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) {
                    Peca piece = tabuleiro.getTabuleiro()[row][col];
                    if (piece != null && piece.getCor().equals(currentPlayer)) {
                        System.out.println("Selected piece at (" + row + ", " + col + "): " + piece);
                        selectedPieceTile = new Point(col, row);
                    }  else if (selectedPieceTile != null) {
                        System.out.println("Selected move at (" + row + ", " + col + ")");
                        var peca=tabuleiro.getPeca(new Posicao(selectedPieceTile.y, selectedPieceTile.x));
                        if(peca.movimentoValido(new Posicao(row, col), tabuleiro)){
                            //INVESTIGAR:Não funciona em alguns Computadores
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
                            //Promocao de peao
                            if(peca.getClass()==Peao.class){
                                Peao p = (Peao) peca;
                                if (p.podePromover(new Posicao(row, col), tabuleiro)){
                                    show=true;
                                    promotedPiecePos = new Posicao(row, col);
                                }
                            }
                            selectedPieceTile = null;
                            loadPieceImages();
                            atualizar();
                        }
                    } else{
                        selectedPieceTile = null;
                    }
                    
                }
                repaint();
            }
        });
    }

    
    private void atualizar(){
        if (show) {
            openImageSelectionDialog(frame);
        }
        this.t=TURN_DURATION_SECONDS;
        partida.mudarTurno();
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
                    g.setColor(MOVE_COLOR);
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    var pos=tabuleiro.calcularMovimentosValidos(tabuleiro.getPeca(new Posicao(row, col)), tabuleiro);
                    for (Posicao movimento : pos) {
                        if(currentPlayer==Cor.PRETO){
                            System.out.println("Movimento: "+movimento.getLinha() + " "+ movimento.getColuna());
                        }
                        g.setPaintMode();
                        g.setXORMode(Color.WHITE);//BUGFIX
                        g.setColor(HIGHLIGHT_COLOR);
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
    
    private void openImageSelectionDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Selecionar Imagem", true);
        dialog.setLayout(new GridLayout(0, 4, 10, 10));
        String[] PROMO_PEAO_OPTIONS_BLACK = {"Resources/sprites/B_QUEEN.png","Resources/sprites/B_TOWER.png","Resources/sprites/B_HORSE.png","Resources/sprites/B_BISHOP.png"};
        String[] PROMO_PEAO_OPTIONS_WHITE = {"Resources/sprites/W_QUEEN.png","Resources/sprites/W_TOWER.png","Resources/sprites/W_HORSE.png","Resources/sprites/W_BISHOP.png"};
        String[] PROMO_PEAO_OPTIONS = Cor.BRANCO==currentPlayer ? PROMO_PEAO_OPTIONS_WHITE:PROMO_PEAO_OPTIONS_BLACK;
        for (String imagePath : PROMO_PEAO_OPTIONS) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JButton imageButton = new JButton(scaledIcon);
            imageButton.setBorder(BorderFactory.createEmptyBorder());
            imageButton.setContentAreaFilled(false);
            imageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Selected image: " + imagePath);
                    if(promotedPiecePos!=null){
                        String extractedPart = imagePath.substring("Resources/sprites/".length(), imagePath.indexOf(".")).split("_")[1];
                        tabuleiro.promoverPeao(promotedPiecePos, extractedPart);
                    }
                    show=false;
                    loadPieceImages();
                    atualizar();
                    dialog.dispose();
                }
            });
            dialog.add(imageButton);
        }

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
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
        Main boardPanel = new Main(partida,currentPlayerLabel,currentTimerLabel,frame);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(sidebar, BorderLayout.EAST);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
    }
}
