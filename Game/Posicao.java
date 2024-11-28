package Game;
/**
 * Posicao é a classe que representa uma posição no tabuleiro.
 * <p>
 * A classe {@code Posicao} representa uma posição no tabuleiro de xadrez.
 * Ela contém dois inteiros, que são a linha e a coluna da posição.
 * </p>
 * @see Tabuleiro
 * @see Peca
 * @see Cor
 * @author chipskein
 */
public class Posicao{
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha(){
        return linha;
    }

    public void setLinha(int linha){
        this.linha = linha;
    }

    public int getColuna(){
        return coluna;
    }

    public void setColuna(int coluna){
        this.coluna = coluna;
    }

    @Override
    public String toString(){
        return linha + ", " + coluna;
    }
}