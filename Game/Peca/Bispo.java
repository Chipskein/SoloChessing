package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public  class Bispo extends Peca {
    public Bispo(Cor cor, Posicao posicao) {
        super(cor, posicao);
    }
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)) {
            return false;
        }
        return super.verificarTrajetoriaDiagonal(destino, tabuleiro);
    }

    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BB";
        }else{
            return "PB";
        }
    }
}