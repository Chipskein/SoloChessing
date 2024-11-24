package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public class Rei extends Peca {
    public Rei(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/sprites/W_KING.png": "/sprites/B_KING.png";
    }
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)) {
            return false;
        }
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());

        // O rei se move apenas uma casa em qualquer direção
        if (deltaX > 1 || deltaY > 1) {
            return false;
        }
        // Verifica se o destino está vazio ou contém uma peça adversária
        Peca pecaDestino = tabuleiro.getPeca(destino);
        if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) {
            return false;
        }
        
        // Verifica se o rei está em xeque ao se mover
        if (tabuleiro.verificarSeReiEstaEmXeque(destino, this.getCor())) {
            return false;
        }        

        return true;
    }
	
    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BR";
        }else{
            return "PR";
        }
    }
}
