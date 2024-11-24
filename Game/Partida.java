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
    }
    public void mudarTurno() throws CloneNotSupportedException{
        jogadorAtual.incrementarMovimentos();
        //se checkmate, fim de jogo
        if (jogadorAtual == jogador1){
            jogadorAtual = jogador2;
            if(tabuleiro.verificarCheckMate(jogador2.getCor())){
                fimDeJogo = true;
                jogador1.setVencedor(true);
                System.out.println("Fim de jogo");
                return;
            };
        }else{
            jogadorAtual = jogador1;
            if(tabuleiro.verificarCheckMate(jogador1.getCor())){
                fimDeJogo = true;
                jogador2.setVencedor(true);
                System.out.println("Fim de jogo");
                return;
            };
        }
    }
    public Jogador getJogadorAtual(){
        return jogadorAtual;
    }

    public Jogador getVencedor(){
        if(jogador1.isVencedor()){
            return jogador1;
        }else if(jogador2.isVencedor()){
            return jogador2;
        }else{
            return null;
        }
    }

    public Tabuleiro getTabuleiro(){
        return tabuleiro;
    }

    public boolean isFimDeJogo(){
        return fimDeJogo;
    }
}
