package Game;

import Game.Peca.Peca;
import java.util.List;

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
        jogadorAtual.incrementarMovimentos();
        if (verificarTurno(jogador1, jogador2) || verificarTurno(jogador2, jogador1)) return;
        jogadorAtual = jogadorAtual.equals(jogador1) ? jogador2 : jogador1;        
    }

    /**
     * Verifica se o turno termina o jogo,caso tenha ocorrido um checkmate
     *
     * @param jogadorAtacado O jogador cuja cor será verificada.
     * @param jogadorVencedor O jogador que será marcado como vencedor em caso de checkmate.
     * @return true se o jogo terminou, false caso contrário.
    */
    private boolean verificarTurno(Jogador jogadorAtacado,Jogador jogadorAtacando){
        if(verificarCheckMate(jogadorAtacado.getCor())){
            fimDeJogo = true;
            jogadorAtacando.setVencedor(true);
            System.out.println("Fim de jogo");
            return true;
        };
        return false;
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

    

    /**
     * Método que verifica se o rei está em xeque mate
     * <p>
     * Verifica se o rei do jogador atual está em xeque
     * Se estiver, verifica se ele pode se mover para alguma posição
     * Se não puder, verifica se alguma peça pode capturar a peça que está dando xeque
     * Se nenhuma peça puder capturar a peça que está dando xeque, é xeque mate
     * </p>
     * @param corJogadorAtual Cor do jogador atual
     * @return boolean
     * @see Cor
    */
    public boolean verificarCheckMate(Cor corJogadorAtual) {
        if (!tabuleiro.verificarSeReiEstaEmXeque(corJogadorAtual)) return false;
        Tabuleiro simulacao;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = tabuleiro.getPeca(new Posicao(i, j));
                if (peca != null && peca.getCor() == corJogadorAtual) {
                    List<Posicao> movimentos = tabuleiro.calcularMovimentosValidos(peca, tabuleiro.clone());
                    for (Posicao movimento : movimentos) {
                        simulacao = tabuleiro.clone();
                        peca.movimentar(movimento, simulacao);
                        if (!tabuleiro.verificarSeReiEstaEmXeque(corJogadorAtual)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
