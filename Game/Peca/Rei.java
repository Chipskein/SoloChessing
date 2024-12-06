package Game.Peca;
import Game.Cor;
import Game.Posicao;
import Game.Tabuleiro;
/**
 * Classe que representa um rei de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public class Rei extends Peca {
    public Rei(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_KING.png": "/Resources/sprites/B_KING.png";
    }
    /**
     * Verifica se um movimento é válido para o rei
     * O rei se move apenas uma casa em qualquer direção
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     * @return true se o movimento é válido, false caso contrário
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        // Verifica se a posição esta dentro do tabuleiro
        if (!super.movimentoValido(destino, tabuleiro)) return false;
        
        // Verifica se o Rei esta se movendo mais de uma casa
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());
        if (deltaX > 1 || deltaY > 1) {
            return false;
        }
        
        // Verifica se o destino tem uma peça da mesma cor
        Peca pecaDestino = tabuleiro.getPeca(destino);
        if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) return false;
        
        //Verifica se Rei fica em xeque?
        if (tabuleiro.verificarSeReiEstaEmXeque(destino, this.getCor())) return false;
        
        return true;
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
        return cor == Cor.BRANCO ? "BR" : "PR";
    }
}
