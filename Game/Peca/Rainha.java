package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
/**
 * Classe que representa uma rainha de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public class Rainha extends Peca {
    public Rainha(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_QUEEN.png": "/Resources/sprites/B_QUEEN.png";
    }
    /**
     * Verifica se um movimento é válido para a rainha
     * A rainha pode se mover em linha reta na vertical ou horizontal e na diagonal sem pular outras peças,quantas casas quiser
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     * @return true se o movimento é válido, false caso contrário
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        // Verifica se a posição esta dentro do tabuleiro
        if (!super.movimentoValido(destino, tabuleiro)) return false;
        
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());

        // Se o movimento é na direção reta(horizontal ou vertical)
        if (deltaX == 0 || deltaY == 0) return super.verificarTrajetoriaRetilinea(destino, tabuleiro);

        // Se o movimento é na direção diagonal
        if (deltaX == deltaY) return super.verificarTrajetoriaDiagonal(destino, tabuleiro);

        return false;
    }

     /**
     * Move a peça para uma nova posição
     * Devem ser feitas as verificações de movimento antes de chamar este método
     * @param destino
     * @param tabuleiro
    */
    @Override
    public void movimentar(Posicao destino, Tabuleiro tabuleiro){
        if(!this.movimentoValido(destino, tabuleiro)){
            throw new IllegalStateException("Movimento inválido, verifique se o movimento é válido antes de chamar o método movimentar"+ " "+toString()+" "+this.posicao+" "+destino);
        }
        super.movimentar(destino, tabuleiro);
    }
	
    @Override
    public String toString(){
        return cor == Cor.BRANCO ? "BQ" : "PQ";
    }
}
