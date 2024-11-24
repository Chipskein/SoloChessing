package Game;

public class Partida {
    private Jogador jogador1;// branco
    private Jogador jogador2;// preto
    private Tabuleiro tabuleiro;
    private Jogador jogadorAtual;
    private boolean fimDeJogo=false;
    /** 
     * Inicia uma nova partida de xadrez
     * @param gameLoop se false, o game loop não é iniciado e a partida é apenas instanciada para testes (MainTestes.java)
    */
    public void iniciarJogo(boolean gameLoop) {
        jogador1 = new Jogador(Cor.BRANCO);
        jogador2 = new Jogador(Cor.PRETO);
        tabuleiro = new Tabuleiro();
        jogadorAtual = jogador1;
        if (!gameLoop) {
            return ;
        }
        //game loop
        //draw board
        //mouse event drag pieces
        //check if game end
        //if is not ended change turn
    }
    public void mudarTurno(){
        if (jogadorAtual == jogador1){
            jogadorAtual = jogador2;
        }else{
            jogadorAtual = jogador1;
        }
    }
    public Jogador getJogadorAtual(){
        return jogadorAtual;
    }

    public Tabuleiro getTabuleiro(){
        return tabuleiro;
    }

    public boolean isFimDeJogo(){
        return fimDeJogo;
    }
}
