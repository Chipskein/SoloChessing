package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
/**
 * Classe que representa um cavalo de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public class Cavalo extends Peca {
    public Cavalo(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_HORSE.png": "/Resources/sprites/B_HORSE.png";
    }
    /**
     * Verifica se um movimento é válido para o cavalo
     * O cavalo se move em "L" pulando peças (duas casas em uma direção e uma em outra)
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     * @return true se o movimento é válido, false caso contrário
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        // Verifica se a posição esta dentro do tabuleiro
        if (!super.movimentoValido(destino, tabuleiro))return false;

        // Verifica se o movimento é em "L"
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            
            // Verifica se a casa de destino está ocupada por uma peça da mesma cor
            Peca pecaDestino = tabuleiro.getPeca(destino);
            if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) return false;

            return true;
        }

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
        return cor == Cor.BRANCO ? "BC" : "PC";
    }
}
