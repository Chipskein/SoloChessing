package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public  class Peao extends Peca {
    public Peao(Cor cor, Posicao posicao) {
        super(cor, posicao);
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

        // Verifica se o peao esta indo para a diagonal
        if (destino.getColuna() != posicao.getColuna()){
            // Verifica se a diferença entre as colunas é maior que 1
            if (Math.abs(destino.getColuna() - posicao.getColuna()) != 1){
                return false;
            }
            // Verifica se a posição de destino é uma posição ocupada por uma peça inimiga
            if (tabuleiro.getPeca(destino) != null && tabuleiro.getPeca(destino).getCor() == cor){
                return false;
            }
        }
        if(Math.abs(destino.getLinha() - posicao.getLinha()) > 1){
            if (posicao.getLinha() != 1 && posicao.getLinha() != 6){
                return false;
            } else if(Math.abs(destino.getLinha() - posicao.getLinha())>2) {
                // o peão não pode se mover mais de 2 casas caso seja o primeiro movimento
                return false;
            }
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
