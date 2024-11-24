package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public  class Peao extends Peca {
    public Peao(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/sprites/W_PAWN.png": "/sprites/B_PAWN.png";
    }
    @Override
    /**
     * Verifica se o movimento é valido para a peça Peao o peão pode se mover para frente ou para a diagonal 1 casa
     * 
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro
     * @return boolean
     * @see Game.Peca.Peca#movimentoValido(Game.Posicao, Game.Tabuleiro)
     */
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)){
            return false;
        };

         // Verifica se o peao está se movendo para trás
            if (
                (cor == Cor.BRANCO && destino.getLinha() >= posicao.getLinha()) || 
                (cor == Cor.PRETO && destino.getLinha() <= posicao.getLinha())
            ) {
            return false;  
        }

        // Verifica se o peao esta indo para a diagonal
        if (destino.getColuna() != posicao.getColuna()){
            // Verifica se a diferença entre as colunas é maior que 1
            if (Math.abs(destino.getColuna() - posicao.getColuna()) != 1){
                return false;
            }
            // Verifica se a posição de destino não é uma posição ocupada por uma peça inimiga
            if (tabuleiro.getPeca(destino) != null && tabuleiro.getPeca(destino).getCor() == cor || tabuleiro.getPeca(destino) == null){
                return false;
            }
        }
        
        if (Math.abs(destino.getLinha() - posicao.getLinha()) > 2) {
            return false;
        }

        if (Math.abs(destino.getLinha() - posicao.getLinha()) == 2) {
            if (posicao.getLinha() != 1 && posicao.getLinha() != 6) {
                return false;
            }
        }

        //Verifica se a posição a frente do peão está ocupada
        if (destino.getColuna() == posicao.getColuna() && tabuleiro.getPeca(destino) != null){
            return false;
        }
        

        return true;
    }
	
    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BP";
        }else{
            return "PP";
        }
    }
}
