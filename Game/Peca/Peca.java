package Game.Peca;

import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
/**
 * Classe abstrata que representa uma peça de xadrez.
 * @param cor Cor da peça
 * @param posicao Posição da peça no tabuleiro
 * @param spritePath Caminho do sprite da peça
 * @param capturada Indica se a peça foi capturada
 * @method movimentoValido Verifica se um movimento é válido
 * @method verificarTrajetoriaDiagonal Verifica se a trajetória de um movimento diagonal é válida
 * @method verificarTrajetoriaRetilinea Verifica se a trajetória de um movimento retilíneo é válida
 * @method movimentar Move a peça para uma nova posição
 * @method getCor Retorna a cor da peça
 * @method getPosicao Retorna a posição da peça
 * @method getSpritePath Retorna o caminho do sprite da peça
 * @method clone Cria uma cópia da peça
 * @author chipskein
 */
public abstract class Peca implements Cloneable{
    protected Cor cor;

    protected Posicao posicao;

    protected String spritePath;

    protected boolean capturada = false;

    public Peca(Cor cor, Posicao posicao) {
        this.cor = cor;
        this.posicao = posicao;
    }

    @Override
    public Peca clone() {
        try {
            Peca clone = (Peca) super.clone();
            clone.posicao = new Posicao(this.posicao.getLinha(), this.posicao.getColuna());
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed. This should never happen.");
            e.printStackTrace();
            return null;
        }
    }
  
    /**
     * Verifica se um movimento é válido para a peça
     * É implementado pelas classes filhas de acordo com o movimento da peça
     * Porém, a classe Peca já implementa a verificação de limites do tabuleiro e se a posição de destino é a mesma da peça
     * @param destino
     * @param tabuleiro
     * @return true se o movimento é válido, false caso contrário
     */
    public boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro){
        // Verifica se a posição esta dentro do tabuleiro
        if (destino.getLinha() < 0 || destino.getLinha() > 7 || destino.getColuna() < 0 || destino.getColuna() > 7){
            return false;
        }
        // Verifica se a posição de destino é a mesma da peça
        if (posicao.equals(destino)){
            return false;
        }
        return true;
    };
    
    /**
     * Verifica se a trajetória de um movimento diagonal é válida
     * Utilizado por peças que se movem em diagonal (Bispo e Rainha)
     * @param destino
     * @param tabuleiro
     * @return true se a trajetória é válida, false caso contrário
     */
    public boolean verificarTrajetoriaDiagonal(Posicao destino, Tabuleiro tabuleiro){
        // Verifica se o movimento é na diagonal
        if (Math.abs(destino.getLinha() - this.posicao.getLinha()) != Math.abs(destino.getColuna() - this.posicao.getColuna())) {
            return false;
        }
        int passoX = (destino.getLinha() > this.posicao.getLinha()) ? 1 : -1;
        int passoY = (destino.getColuna() > this.posicao.getColuna()) ? 1 : -1;
        // Percorre as casas entre a posição atual e a posição de destino
        int x = this.posicao.getLinha()+passoX;
        int y = this.posicao.getColuna()+passoY;
        while (x != destino.getLinha() && y != destino.getColuna()) {
            // Verifica se está dentro dos limites do tabuleiro
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                return false; // Fora dos limites
            }
            Peca pecaNoCaminho = tabuleiro.getPeca(new Posicao(x, y));
            if (pecaNoCaminho != null && !pecaNoCaminho.equals(this)) {
                return false;
            }
            x += passoX;
            y += passoY;
        }
        // Verifica se o destino está vazio ou contém uma peça adversária
        Peca pecaDestino = tabuleiro.getPeca(destino);
        if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) {
            return false;
        }
        return true;
    }
    
    /**
     * Verifica se a trajetória de um movimento retilíneo é válida
     * Utilizado por peças que se movem em linha reta (Torre e Rainha)
     * @param destino
     * @param tabuleiro
     * @return
     */
    public boolean verificarTrajetoriaRetilinea(Posicao destino, Tabuleiro tabuleiro){
        // Verifica se o movimento é na diagonal
        if (Math.abs(destino.getLinha() - this.posicao.getLinha()) == Math.abs(destino.getColuna() - this.posicao.getColuna())) {
            return false;
        }
        int passoX = 0, passoY = 0;
        if (destino.getLinha() > this.posicao.getLinha()) passoX = 1;
        else if (destino.getLinha() < this.posicao.getLinha()) passoX = -1;
        if (destino.getColuna() > this.posicao.getColuna()) passoY = 1;
        else if (destino.getColuna() < this.posicao.getColuna()) passoY = -1;
        // Percorre as casas entre a posição atual e a posição de destino
        int x = this.posicao.getLinha() + passoX;
        int y = this.posicao.getColuna() + passoY;
        while (x != destino.getLinha() || y != destino.getColuna()) {
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                return false; // Fora dos limites
            }
            Peca pecaNoCaminho = tabuleiro.getPeca(new Posicao(x, y));
            if (pecaNoCaminho != null) {
                return false;
            }
            x += passoX;
            y += passoY;
        }
        // Verifica se o destino está vazio ou contém uma peça adversária
        Peca pecaDestino = tabuleiro.getPeca(destino);
        if (pecaDestino != null && pecaDestino.getCor() == this.getCor()) {
            return false;
        }
        return true;
    }

    /**
     * Move a peça para uma nova posição
     * Devem ser feitas as verificações de movimento antes de chamar este método
     * Caso a posição de destino contenha uma peça adversária, a peça é capturada
     * Caso a peça movida seja um rei, atualiza a posição do rei no tabuleiro
     * @param destino
     * @param tabuleiro
     */
    public void movimentar(Posicao destino, Tabuleiro tabuleiro){
        Peca dest=tabuleiro.getTabuleiro()[destino.getLinha()][destino.getColuna()];
        if(dest!=null) dest.capturada=true;
        tabuleiro.getTabuleiro()[destino.getLinha()][destino.getColuna()]=this;
        tabuleiro.getTabuleiro()[this.posicao.getLinha()][this.posicao.getColuna()]=null;
        if(this.posicao.equals(tabuleiro.getReiBrancoPosicao())) tabuleiro.setReiBrancoPosicao(destino);
        if(this.posicao.equals(tabuleiro.getReiPretoPosicao())) tabuleiro.setReiPretoPosicao(destino);
        this.posicao=destino;
    }
    
    public Cor getCor(){
        return cor;
    };    

    public Posicao getPosicao(){
        return posicao;
    }

    public String getSpritePath(){
        return spritePath;
    }
}
