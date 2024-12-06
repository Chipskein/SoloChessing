package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
/**
 * Classe que representa uma torre de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public class Torre extends Peca {
    public Torre(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_TOWER.png": "/Resources/sprites/B_TOWER.png";
    }
    /**
     * Verifica se um movimento é válido para a torre
     * A torre pode se mover em linha reta na vertical ou horizontal, sem pular outras peças,quantas casas quiser
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     * @return true se o movimento é válido, false caso contrário
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)){
            return false;
        };
        return super.verificarTrajetoriaRetilinea(destino, tabuleiro);
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
        return cor == Cor.BRANCO ? "BT" : "PT";
    }
}

