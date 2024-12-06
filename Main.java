import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;
import Game.Peca.Peca;
import Game.Peca.Rei;
import Game.Peca.Peao;
import Game.Cor;
import Game.Partida;
import Game.Posicao;
import Game.PromocaoPeaoTipo;

import java.util.Timer;

public class Main extends JPanel {
    private static final int QUADRADO_TAMANHO = 100;
    private static final int TAMANHO_TABULEIRO = 8;
    private static final long DURACAO_TURNO_SEGUNDOS = 100;
    private static final Color HIGHLIGHT_COLOR = new Color(8, 200, 0, 128);
    private static final Color MOVE_COLOR = new Color(255, 200, 0, 128);

    private final Partida partida;
    private final JLabel playerLabel;
    private final JLabel timerLabel;
    private final JFrame frame;

    private Image[][] sprites;
    private Clip quadroFX;
    private Cor player=Cor.BRANCO;
    private Point pecaSelecionada = null;
    private Posicao posPecaPromovida = null;
    private boolean mostraModalPromacaoPeao = false;
    private long t = DURACAO_TURNO_SEGUNDOS;
    private List<Posicao> movimentosPossiveis= new ArrayList<Posicao>();

    public Main(Partida partida, JLabel playerLabel, JLabel timerLabel, JButton desistenciaBotao,JFrame frame) {
        this.partida = partida;
        this.playerLabel = playerLabel;
        this.timerLabel = timerLabel;
        this.frame = frame;
        
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!mostraModalPromacaoPeao&&t>0){
                    t--;
                    updateTimerLabel();
                } else if (t==0){
                    atualizar();
                }
            }
        };
        timer.scheduleAtFixedRate(task, new Date(), 1000);

        setPreferredSize(new Dimension(QUADRADO_TAMANHO* TAMANHO_TABULEIRO, QUADRADO_TAMANHO* TAMANHO_TABULEIRO));
        updatePlayerLabel();
        loadSprites();
        loadAudio();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / QUADRADO_TAMANHO;
                int row = e.getY() / QUADRADO_TAMANHO;
                if (col >= 0 && col < TAMANHO_TABULEIRO && row >= 0 && row < TAMANHO_TABULEIRO) {
                    Peca piece = partida.getTabuleiro().getTabuleiro()[row][col];
                    if (piece != null && piece.getCor().equals(player)) {
                        System.out.println("Selected piece at (" + row + ", " + col + "): " + piece);
                        pecaSelecionada = new Point(col, row);
                    }  else if (pecaSelecionada != null) {
                        System.out.println("Selected move at (" + row + ", " + col + ")");
                        var peca=partida.getTabuleiro().getPeca(new Posicao(pecaSelecionada.y, pecaSelecionada.x));
                        for (Posicao movimento : movimentosPossiveis) {
                            if(movimento.getLinha()==row&&movimento.getColuna()==col){
                                if (quadroFX != null && quadroFX.isOpen()) {
                                    if (quadroFX.isRunning()) {
                                        quadroFX.stop();
                                    }
                                    System.out.println(quadroFX);
                                    System.out.println("Playing sound");
                                    quadroFX.setFramePosition(0);
                                    quadroFX.start();
                                }
                                peca.movimentar(new Posicao(row, col),partida.getTabuleiro());
                                //Promocao de peao
                                if(peca.getClass()==Peao.class){
                                    Peao p = (Peao) peca;
                                    if (p.podePromover(new Posicao(row, col), partida.getTabuleiro())){
                                        mostraModalPromacaoPeao=true;
                                        posPecaPromovida = new Posicao(row, col);
                                    }
                                }
                                pecaSelecionada = null;
                                loadSprites();
                                atualizar();
                                break;
                            }
                        }
                    } else{
                        pecaSelecionada = null;
                    }
                    
                }
                repaint();
            }
        });

        desistenciaBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cor vencedor = player == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO;
                JOptionPane.showMessageDialog(frame, 
                    "O jogador " + (vencedor == Cor.BRANCO ? "Branco" : "Preto") + " venceu por desistência!", 
                    "Fim de Jogo", 
                    JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }

    
    private void atualizar(){
        if (mostraModalPromacaoPeao) mostrarModalPromocaoPeao(frame);
        pecaSelecionada = null;
        posPecaPromovida = null;
        this.t=DURACAO_TURNO_SEGUNDOS;
        partida.mudarTurno();
        player=partida.getJogadorAtual().getCor();
        updatePlayerLabel();
        if (partida.isFimDeJogo()) {
            JOptionPane.showMessageDialog(Main.this, "Fim de Jogo! " + partida.getVencedor().getCor() + " venceu! com "+ partida.getVencedor().getMovimentos()+" movimentos", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        repaint(); 
    }
    
    private void updatePlayerLabel() {
        playerLabel.setText("Jogador Atual: " + (player == Cor.BRANCO ? "Branco" : "Preto"));
    }

    private void updateTimerLabel() {
        timerLabel.setText("Timer: "+t);
    }
 
    private void loadAudio(){
        try{
            InputStream audioSrc = getClass().getResourceAsStream("/Resources/audios/boardFX.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            quadroFX = AudioSystem.getClip();
            quadroFX.open(audioStream);
            System.out.println("Audio loaded");
            quadroFX.setFramePosition(0);
        } catch (Exception e) {
            System.out.println("Error loading audio: " + e.getMessage());
        }

    }

    private void loadSprites() {
        sprites = new Image[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
        for (Peca[] pecas : this.partida.getTabuleiro().getTabuleiro()) {
            for (Peca peca : pecas) {
                if (peca != null) {
                    try {
                        URL url = getClass().getResource(peca.getSpritePath());
                        if (url != null) {
                            Image image = Toolkit.getDefaultToolkit().getImage(url);
                            sprites[peca.getPosicao().getLinha()][peca.getPosicao().getColuna()] = image;
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
        for (int row = 0; row < TAMANHO_TABULEIRO; row++) {
            for (int col = 0; col < TAMANHO_TABULEIRO; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(col * QUADRADO_TAMANHO, row * QUADRADO_TAMANHO, QUADRADO_TAMANHO, QUADRADO_TAMANHO);
                
                if (pecaSelecionada != null && pecaSelecionada.x == col && pecaSelecionada.y == row) {
                    g.setColor(MOVE_COLOR);
                    g.fillRect(col * QUADRADO_TAMANHO, row * QUADRADO_TAMANHO, QUADRADO_TAMANHO, QUADRADO_TAMANHO);
                    var pos=partida.getTabuleiro().calcularMovimentosValidos(partida.getTabuleiro().getPeca(new Posicao(row, col)), partida.getTabuleiro());
                    movimentosPossiveis=pos;
                    for (Posicao movimento : pos) {
                        g.setPaintMode();
                        g.setXORMode(Color.WHITE);//BUGFIX:Highlighting tiles
                        g.setColor(HIGHLIGHT_COLOR);
                        g.fillRect(movimento.getColuna() * QUADRADO_TAMANHO, movimento.getLinha() * QUADRADO_TAMANHO, QUADRADO_TAMANHO, QUADRADO_TAMANHO);
                        System.out.println("Highlighting tile: (" + movimento.getLinha() + ", " + movimento.getColuna() + ")");
                        
                        //BUGFIX:Rei adversario em xeque-mate por não ter turno para se mover devido ao timer
                        var pecaInPos=partida.getTabuleiro().getPeca(movimento);
                        if(pecaInPos!=null&&pecaInPos.getClass()==Rei.class){
                            System.out.println("Rei adversario em xeque-mate por não ter turno para se mover!");
                            partida.getJogadorAtual().setVencedor(true);
                            partida.mudarTurno();
                        }
                    }
                }

                if (sprites[row][col] != null) {
                    g.drawImage(sprites[row][col], col * QUADRADO_TAMANHO, row * QUADRADO_TAMANHO, QUADRADO_TAMANHO, QUADRADO_TAMANHO, this);
                }
            }
        }
        
    }
    
    private void mostrarModalPromocaoPeao(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Selecionar Imagem", true);
        dialog.setLayout(new GridLayout(0, 4, 10, 10));
        String[] PROMO_PEAO_OPTIONS_BLACK = {"Resources/sprites/B_QUEEN.png","Resources/sprites/B_TOWER.png","Resources/sprites/B_HORSE.png","Resources/sprites/B_BISHOP.png"};
        String[] PROMO_PEAO_OPTIONS_WHITE = {"Resources/sprites/W_QUEEN.png","Resources/sprites/W_TOWER.png","Resources/sprites/W_HORSE.png","Resources/sprites/W_BISHOP.png"};
        String[] PROMO_PEAO_OPTIONS = Cor.BRANCO==player ? PROMO_PEAO_OPTIONS_WHITE:PROMO_PEAO_OPTIONS_BLACK;
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
                    if(posPecaPromovida!=null){
                        String extractedPart = imagePath.substring("Resources/sprites/".length(), imagePath.indexOf(".")).split("_")[1];
                        partida.promoverPeao(posPecaPromovida, PromocaoPeaoTipo.fromString(extractedPart));
                    }
                    mostraModalPromacaoPeao=false;
                    loadSprites();
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
        
        JButton surrenderButton = new JButton("Desistir");
        surrenderButton.setFont(new Font("Arial", Font.BOLD, 16));
        surrenderButton.setFocusable(false);
        
        JLabel currentPlayerLabel = new JLabel("Jogador Atual: Branco");
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel currentTimerLabel = new JLabel("Timer: "+DURACAO_TURNO_SEGUNDOS);
        currentTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentTimerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, TAMANHO_TABULEIRO * QUADRADO_TAMANHO));
        sidebar.add(currentPlayerLabel, BorderLayout.CENTER);
        sidebar.add(currentTimerLabel, BorderLayout.PAGE_END);
        sidebar.add(surrenderButton, BorderLayout.PAGE_START);

        Main boardPanel = new Main(partida,currentPlayerLabel,currentTimerLabel,surrenderButton,frame);
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
