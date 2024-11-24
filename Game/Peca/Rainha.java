package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public class Rainha extends Peca {
    public Rainha(Cor cor, Posicao posicao) {
        super(cor, posicao);
    }
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)) {
            return false;
        }
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());
        
        // Verifica se o movimento é na direção horizontal ou vertical
        if (deltaX == 0 || deltaY == 0) {
            return super.verificarTrajetoriaRetilinea(destino, tabuleiro);
        }

        // Verifica se o movimento é na direção diagonal
        if (deltaX == deltaY) {
            return super.verificarTrajetoriaDiagonal(destino, tabuleiro);
        }
        return false;
    }
	
    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BRA";
        }else{
            return "PRA";
        }
    }
}
