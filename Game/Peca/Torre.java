package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public class Torre extends Peca {
    public Torre(Cor cor, Posicao posicao) {
        super(cor, posicao);
    }
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)){
            return false;
        };
        return super.verificarTrajetoriaRetilinea(destino, tabuleiro);
    }
   
    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BT";
        }else{
            return "PT";
        }
    }
}

