package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;

/**
 * Classe que representa um bispo de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public  class Bispo extends Peca {
    public Bispo(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_BISHOP.png": "/Resources/sprites/B_BISHOP.png";
    }
    /**
     * Verifica se um movimento é válido para o bispo
     * O bispo se move em linha na diagonal sem pular outras peças,quantas casas quiser
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        // Verifica se a posição esta dentro do tabuleiro
        if (!super.movimentoValido(destino, tabuleiro)) return false;
        return super.verificarTrajetoriaDiagonal(destino, tabuleiro);
    }

    @Override
    public String toString(){
        return cor == Cor.BRANCO ? "BB" : "PB";
    }
}
