package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public class Cavalo extends Peca {
    public Cavalo(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/sprites/W_HORSE.png": "/sprites/B_HORSE.png";
    }
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        if (!super.movimentoValido(destino, tabuleiro)){
            return false;
        };
        // cavalo se move em L pulando peças
        /* 
            (i−2,j−1) → 2 casas para cima, 1 para a esquerda
            (i−2,j+1)(i−2,j+1) → 2 casas para cima, 1 para a direita
            (i−1,j−2)(i−1,j−2) → 1 casa para cima, 2 para a esquerda
            (i−1,j+2)(i−1,j+2) → 1 casa para cima, 2 para a direita
            (i+1,j−2)(i+1,j−2) → 1 casa para baixo, 2 para a esquerda
            (i+1,j+2)(i+1,j+2) → 1 casa para baixo, 2 para a direita
            (i+2,j−1)(i+2,j−1) → 2 casas para baixo, 1 para a esquerda
            (i+2,j+1)(i+2,j+1) → 2 casas para baixo, 1 para a direita
        */
         // Verifica a diferença em relação à posição atual
        int deltaX = Math.abs(this.posicao.getLinha() - destino.getLinha());
        int deltaY = Math.abs(this.posicao.getColuna() - destino.getColuna());

        // O cavalo move em "L" (duas casas em uma direção e uma em outra)
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            // Verifica se a casa de destino está ocupada por uma peça da mesma cor
            Peca pecaDestino = tabuleiro.getPeca(destino);
            if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) {
                return false;
            }
            return true;
        }

        return false;
        
    }

    @Override
    public String toString(){
        if (cor == Cor.BRANCO){
            return "BC";
        }else{
            return "PC";
        }
    }
}
