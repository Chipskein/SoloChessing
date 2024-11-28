package Game;

/**
 * Jogador é a classe que representa um jogador de xadrez.
 * <p>
 * A classe {@code Jogador} representa um jogador de xadrez.
 * Ele contém a cor do jogador, se ele é o vencedor e quantos movimentos ele fez.
 * </p>
 * @see Cor
 * @see Tabuleiro
 * @see Peca
 * @see Partida
 * @author chipskein
 */
public class Jogador {
    private Cor cor;
    private boolean vencedor;
    private int movimentos;

    public Jogador(Cor cor) {
        this.cor = cor;
        this.movimentos = 0;
        this.vencedor = false;
    }

    public Cor getCor() {
        return  cor;
    }

    public boolean isVencedor() {
        return vencedor;
    }

    public void setVencedor(boolean vencedor) {
        this.vencedor = vencedor;
    }

    public int getMovimentos() {
        return movimentos;
    }

    public void incrementarMovimentos() {
        this.movimentos++;
    }

}
