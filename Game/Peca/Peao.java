package Game.Peca;
import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
/**
 * Classe que representa um peão de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @author chipskein
 */
public  class Peao extends Peca {
    public Peao(Cor cor, Posicao posicao) {
        super(cor, posicao);
        this.spritePath = cor==Cor.BRANCO ? "/Resources/sprites/W_PAWN.png": "/Resources/sprites/B_PAWN.png";
    }
    /**
     * Verifica se um movimento é válido para o peão
     * O peão se move apenas uma casa para frente, exceto no primeiro movimento que pode se mover duas casas
     * O peão captura peças na diagonal
     * @param destino Posição de destino
     * @param tabuleiro Tabuleiro do jogo
     * @return true se o movimento é válido, false caso contrário
     */
    @Override
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro) {
        // Verifica se a posição esta dentro do tabuleiro
        if (!super.movimentoValido(destino, tabuleiro))return false;


        // Verifica se o peao está se movendo para trás
        if ((cor == Cor.BRANCO && destino.getLinha() >= posicao.getLinha()) || (cor == Cor.PRETO && destino.getLinha() <= posicao.getLinha())) {
            return false;  
        }

        //Se o peão está se movendo na diagonal
        if (destino.getColuna() != posicao.getColuna()){

            // Verifica se a diferença entre as colunas é maior que 1
            if (Math.abs(destino.getColuna() - posicao.getColuna()) != 1) return false;
            
            // Verifica se a posição de destino está ocupada por uma peça da mesma cor ou se está vazia
            if (tabuleiro.getPeca(destino) != null && tabuleiro.getPeca(destino).getCor() == cor || tabuleiro.getPeca(destino) == null) return false;

        }
        
        // Verifica se o peão está se movendo mais de duas casas
        if (Math.abs(destino.getLinha() - posicao.getLinha()) > 2) return false;
        
        // Se o peão está se movendo duas casas
        if (Math.abs(destino.getLinha() - posicao.getLinha()) == 2) {

            // Verifica se o peão está na posição inicial
            if (posicao.getLinha() != 1 && posicao.getLinha() != 6) return false;
            
            // Verifica se a posição a frente do peão está ocupada e se a posição a duas casas a frente do peão está ocupada
            if (tabuleiro.getPeca(new Posicao((destino.getLinha() + posicao.getLinha()) / 2, destino.getColuna())) != null || tabuleiro.getPeca(destino) != null) {
                return false;
            }

        }

        //Verifica se a posição a frente do peão está ocupada
        if (destino.getColuna() == posicao.getColuna() && tabuleiro.getPeca(destino) != null) return false;


        return true;
    }


    /**
     * Verifica se o peão pode ser promovido
     * rodar essa função antes de mover o peão
     * @param destino
     * @param tabuleiro
     * @return se o peão pode ser promovido nessa posição
     */
    public boolean podePromover(Posicao destino,Tabuleiro tabuleiro){
        // é promovido quando chega do outro lado do tabuleiro
        // Branco começa na linha 7
        // Preto na linha 0
        if(cor==Cor.BRANCO){
            return destino.getLinha()==0;
        } else{
            return destino.getLinha()==7;
        }
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
        return cor == Cor.BRANCO ? "BP" : "PP";
    }
}
