package Game;

/**
 * Partida é a classe que representa uma partida de xadrez.
 * <p>
 * A classe {@code Partida} representa uma partida de xadrez.
 * Ela contém dois jogadores, um tabuleiro e o jogador atual.
 * </p>
 * @see Jogador
 * @see Tabuleiro
 * @author chipskein
 */
public class Partida {
    /**
     * Jogador 1
     * <p>
     * Jogador 1 é o jogador que joga com as peças brancas.
     * </p>
     */
    private Jogador jogador1;// branco
    /**
     * Jogador 2
     * <p>
     * Jogador 2 é o jogador que joga com as peças pretas.
     * </p>
     */
    private Jogador jogador2;

    /**
     * Tabuleiro
     * <p>
     * Tabuleiro é o tabuleiro de xadrez da partida.
     * </p>
     */
    private Tabuleiro tabuleiro;

    /**
     * Jogador Atual
     * <p>
     * Jogador Atual é o jogador que está jogando no momento.
     * </p>
     */
    private Jogador jogadorAtual;

    /**
     * Fim de Jogo
     * <p>
     * Fim de Jogo é uma variável que indica se a partida acabou.
     * </p>
     */
    private boolean fimDeJogo=false;

    /**
     * Iniciar Jogo
     * <p>
     * Iniciar Jogo é o método que inicia a partida.
     * Ele cria os jogadores, o tabuleiro e define o jogador atual.
     * </p>
     */
    public void iniciarJogo() {
        jogador1 = new Jogador(Cor.BRANCO);
        jogador2 = new Jogador(Cor.PRETO);
        tabuleiro = new Tabuleiro();
        jogadorAtual = jogador1;
    }

    /**
     * Mudar Turno
     * <p>
     * Mudar Turno é o método que muda o turno do jogador.
     * Ele verifica se o jogador atual está em xeque-mate.
     * Se estiver, o jogo acaba e o outro jogador vence.
     * </p>
     */
    public void mudarTurno(){
        //TODO:REFACTOR?
        jogadorAtual.incrementarMovimentos();
        if(tabuleiro.verificarCheckMate(jogador1.getCor())){
            fimDeJogo = true;
            jogador2.setVencedor(true);
            System.out.println("Fim de jogo");
            return;
        };
        if(tabuleiro.verificarCheckMate(jogador2.getCor())){
            fimDeJogo = true;
            jogador1.setVencedor(true);
            System.out.println("Fim de jogo");
            return;
        };
        if(jogadorAtual.equals(jogador1)){
            jogadorAtual = jogador2;
        } else{
            jogadorAtual = jogador1;
        }

    }
    
    
    public Jogador getJogadorAtual(){
        return jogadorAtual;
    }

    public Tabuleiro getTabuleiro(){
        return tabuleiro;
    }

    /**
     * Método que retorna o vencedor da partida
     * <p>
     * Retorna o jogador que venceu a partida.
     * Se não houver vencedor, retorna null.
     * </p>
     * @return Jogador
     * @see Jogador
     */
    public Jogador getVencedor(){
        if(jogador1.isVencedor()){
            return jogador1;
        }else if(jogador2.isVencedor()){
            return jogador2;
        }else{
            return null;
        }
    }

    /**
     * Método que verifica se a partida acabou
     * <p>
     * Retorna true se a partida acabou, false caso contrário.
     * </p>
     * @return boolean
     */
    public boolean isFimDeJogo(){
        return fimDeJogo;
    }
}
