package Game;

public class Partida {
    private Jogador jogador1;// branco
    private Jogador jogador2;// preto
    private Tabuleiro tabuleiro;
    private Jogador jogadorAtual;
    private boolean fimDeJogo=false;
    public void iniciarJogo() {
        jogador1 = new Jogador(Cor.BRANCO);
        jogador2 = new Jogador(Cor.PRETO);
        tabuleiro = new Tabuleiro();
        jogadorAtual = jogador1;
    }
    public void mudarTurno() throws CloneNotSupportedException{
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
