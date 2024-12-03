package Game;
import Game.Peca.Bispo;
import Game.Peca.Cavalo;
import Game.Peca.Peao;
import Game.Peca.Peca;
import Game.Peca.Rainha;
import Game.Peca.Rei;
import Game.Peca.Torre;
import java.util.ArrayList;
import java.util.List;
/**
 * Tabuleiro é a classe que representa o tabuleiro de xadrez.
 *
 * <p>
 * A classe {@code Tabuleiro} representa um tabuleiro de xadrez.
 * Ele contém uma matriz 8x8, que são as peças de xadrez que
 * estão atualmente no tabuleiro. O tabuleiro é inicializado com as
 * peças padrão de um jogo de xadrez.
 * Podendo ser inicializado com um tabuleiro customizado usando {@code Tabuleiro([][]Peca)}.
 * </p>
 *
 * @author chipskein
 * @see Peca
 * @see Posicao
 * @see Cor
 */
public class Tabuleiro implements Cloneable{
    private final int LINHAS=8;
    private final int COLUNAS=8;
    /**
     * Posição do rei preto
     * para facilitar a verificação de xeque
    */
    private Posicao reiPretoPosicao=null;
    /**
     * Posição do rei branco
     * para facilitar a verificação de xeque
    */
    private Posicao reiBrancoPosicao=null;
    /*
     * as primeiras duas linhas(0-1) são as peças pretas
     * as últimas duas linhas(6-7) são as peças brancas
    */
    private Peca[][] tabuleiro;

    public Tabuleiro(){
        tabuleiro = new Peca[LINHAS][COLUNAS];
        /*   0 1 2 3  4  5 6 7
           0 T C B RA R B C T         
           1 P P P P P  P P P
           XXXXXXXXXXXXXXXXXX
           6 P P P P P  P P P
           7 T C B RA R B C T

        */
        tabuleiro[0][0] = new Torre(Cor.PRETO, new Posicao(0,0));
        tabuleiro[0][1] = new Cavalo(Cor.PRETO, new Posicao(0,1));
        tabuleiro[0][2] = new Bispo(Cor.PRETO, new Posicao(0,2));
        tabuleiro[0][3] = new Rainha(Cor.PRETO, new Posicao(0,3));
        tabuleiro[0][4] = new Rei(Cor.PRETO, new Posicao(0,4));
        reiPretoPosicao = new Posicao(0,4);
        tabuleiro[0][5] = new Bispo(Cor.PRETO, new Posicao(0,5));
        tabuleiro[0][6] = new Cavalo(Cor.PRETO, new Posicao(0,6));
        tabuleiro[0][7] = new Torre(Cor.PRETO, new Posicao(0,7));
        for (int i = 0; i < COLUNAS; i++){
            tabuleiro[1][i] = new Peao(Cor.PRETO, new Posicao(1,i));
            tabuleiro[6][i] = new Peao(Cor.BRANCO, new Posicao(6,i));
        }
        tabuleiro[7][0] = new Torre(Cor.BRANCO, new Posicao(7,0));
        tabuleiro[7][1] = new Cavalo(Cor.BRANCO, new Posicao(7,1));
        tabuleiro[7][2] = new Bispo(Cor.BRANCO, new Posicao(7,2));
        tabuleiro[7][3] = new Rainha(Cor.BRANCO, new Posicao(7,3));
        tabuleiro[7][4] = new Rei(Cor.BRANCO, new Posicao(7,4));
        reiBrancoPosicao = new Posicao(7,4);
        tabuleiro[7][5] = new Bispo(Cor.BRANCO, new Posicao(7,5));
        tabuleiro[7][6] = new Cavalo(Cor.BRANCO, new Posicao(7,6));
        tabuleiro[7][7] = new Torre(Cor.BRANCO, new Posicao(7,7));
    }
    
    public Tabuleiro(Peca[][] tabuleiro){
        this.tabuleiro = tabuleiro;
    }

    /**
     * Método que retorna uma cópia do tabuleiro
     * Para realizar simulações sem alterar o tabuleiro atual
     * @return Tabuleiro
    */
    @Override
    public Tabuleiro clone() {
        Peca[][] tabuleiroClone = new Peca[LINHAS][COLUNAS];
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                Peca peca = tabuleiro[i][j];
                tabuleiroClone[i][j] = (peca != null) ? peca.clone() : null;
            }
        }
        var t=new Tabuleiro(tabuleiroClone);
        t.setReiPretoPosicao(new Posicao(reiPretoPosicao.getLinha(),reiPretoPosicao.getColuna()));
        t.setReiBrancoPosicao(new Posicao(reiBrancoPosicao.getLinha(),reiBrancoPosicao.getColuna()));
        return t;
    }

    public Posicao getReiPretoPosicao() {
        return reiPretoPosicao;
    }

    public void setReiPretoPosicao(Posicao reiPretoPosicao) {
        this.reiPretoPosicao = reiPretoPosicao;
    }
    
    public Posicao getReiBrancoPosicao() {
        return reiBrancoPosicao;
    }

    public void setReiBrancoPosicao(Posicao reiBrancoPosicao) {
        this.reiBrancoPosicao = reiBrancoPosicao;
    }
 
    public Peca[][] getTabuleiro(){
        return tabuleiro;
    }
    
    public Peca getPeca(Posicao posicao){
        return tabuleiro[posicao.getLinha()][posicao.getColuna()];
    }

    public int getLINHAS(){
        return LINHAS;
    }

    public int getCOLUNAS(){
        return COLUNAS;
    }

    /**
     * Método que calcula os movimentos válidos de uma peça
     * @param peca Peça que deseja calcular os movimentos válidos
     * @param tabuleiro Tabuleiro atual
     * @return List<Posicao>
     * @see Peca
     * @see Posicao
     * @see Tabuleiro
    */
    public List<Posicao> calcularMovimentosValidos(Peca peca, Tabuleiro tabuleiro) {
        List<Posicao> movimentosValidos = new ArrayList<>();
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                Posicao destino = new Posicao(i, j);
                if (peca.movimentoValido(destino, tabuleiro)) {
                    movimentosValidos.add(destino);
                }
            }
        }
        return movimentosValidos;
    }

    /**
     * Método que promove um peão para uma peça de outro tipo
     * @param peaoPos Posição do peão
     * @param tipo Tipo da peça que o peão será promovido (RAINHA, TORRE, BISPO, CAVALO)
     * @see PromocaoPeaoTipo
     * @return void
    */
    public void promoverPeao(Posicao peaoPos,PromocaoPeaoTipo tipo){
        Peca peao = tabuleiro[peaoPos.getLinha()][peaoPos.getColuna()];
        Peca novaPeca = null;
        switch (tipo){
            case RAINHA:
                novaPeca = new Rainha(peao.getCor(),peao.getPosicao());
                break;
            case TORRE:
                novaPeca = new Torre(peao.getCor(),peao.getPosicao());
                break;
            case CAVALO:
                novaPeca = new Cavalo(peao.getCor(),peao.getPosicao());
                break;
            case BISPO:
                novaPeca = new Bispo(peao.getCor(),peao.getPosicao());
                break;
            default:
                return;
        }
        tabuleiro[peao.getPosicao().getLinha()][peao.getPosicao().getColuna()] = novaPeca;
    }


    /**
     * Metodo que verifica se o rei está em xeque
     * <p>
     * Verifica se o rei está em xeque, percorrendo o tabuleiro e verificando se alguma peça adversária tem algum movimento válido para a posição do rei
     * </p>
     * @param posicaoRei Posição do rei
     * @param corRei Cor do rei
     * @return boolean
     * @see Posicao
     * @see Cor
    */
    
    public boolean verificarSeReiEstaEmXeque(Posicao posicaoRei,Cor corRei) {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                Peca pecaAdversaria = this.getPeca(new Posicao(i, j));
                if (pecaAdversaria != null && pecaAdversaria.getCor() != corRei) {
                    if (pecaAdversaria.movimentoValido(posicaoRei, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean verificarSeReiEstaEmXeque(Cor corRei) {
        Posicao posicaoRei = (corRei == Cor.BRANCO) ? this.getReiBrancoPosicao() : this.getReiPretoPosicao();
        return verificarSeReiEstaEmXeque(posicaoRei,corRei);
    }
    
    /**
     * Método que retorna uma string representando o tabuleiro
     * Para facilitar a visualização do tabuleiro no console
     * @return String
    */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("   ");
        for (int j = 0; j < COLUNAS; j++) {
            s.append(j).append("   ");
        }
        s.append("\n");
        for (int i = 0; i < LINHAS; i++) {
            s.append(i).append(" | ");
            for (int j = 0; j < COLUNAS; j++) {
                if (tabuleiro[i][j] == null) {
                    s.append("   |");
                } else {
                    s.append(tabuleiro[i][j].toString()).append(" |");
                }
            }
    
            s.append("\n");
        }
        return s.toString();
    }
    
    
}
