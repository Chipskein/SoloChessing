package Game;
import Game.Peca.Peca;
import Game.Peca.Torre;
import Game.Peca.Cavalo;
import Game.Peca.Peao;

import java.util.ArrayList;
import java.util.List;

import Game.Peca.Bispo;
import Game.Peca.Rainha;
import Game.Peca.Rei;
public class Tabuleiro implements Cloneable{
    private int linhas=8;
    private int colunas=8;
    //posições dos reis para facilitar a verificação de xeque
    private Posicao reiPretoPosicao=null;
    private Posicao reiBrancoPosicao=null;
    
    @Override
    public Tabuleiro clone() {
        Peca[][] tabuleiroClone = new Peca[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peca peca = tabuleiro[i][j];
                if (peca != null) {
                    tabuleiroClone[i][j] = peca.clone();
                } else {
                    tabuleiroClone[i][j] = null;
                }
            }
        }
        return new Tabuleiro(tabuleiroClone);
    }
    
    /*
     * tabuleiro é uma matriz de peças
     * as primeiras duas linhas(0-1) são as peças pretas
     * as últimas duas linhas(6-7) são as peças brancas
     */
    private Peca[][] tabuleiro;

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
    public Tabuleiro(){
        tabuleiro = new Peca[linhas][colunas];
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
        for (int i = 0; i < colunas; i++){
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
    public Peca[][] getTabuleiro(){
        return tabuleiro;
    }
    public Tabuleiro(Peca[][] tabuleiro){
        this.tabuleiro = tabuleiro;
    }


    public boolean verificarSeReiEstaEmXeque(Posicao posicaoRei, Cor corRei) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peca pecaAdversaria = tabuleiro[i][j];
                if (pecaAdversaria != null && pecaAdversaria.getCor() != corRei) {
                    if (pecaAdversaria.movimentoValido(posicaoRei, this)) {
                        System.out.println("Rei em cheque("+posicaoRei+") "+ pecaAdversaria.getClass()+ "("+pecaAdversaria.getPosicao()+")");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Posicao> calcularMovimentosValidos(Peca peca, Tabuleiro tabuleiro) {
        List<Posicao> movimentosValidos = new ArrayList<>();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Posicao destino = new Posicao(i, j);
                if (peca.movimentoValido(destino, tabuleiro)) {
                    movimentosValidos.add(destino);
                }
            }
        }
        return movimentosValidos;
    }

    public boolean verificarCheckMate(Cor corJogadorAtual){
        //verifica se o rei do jogador atual está em xeque
        //se estiver, verifica se ele pode se mover para alguma posição
        //se não puder, verifica se alguma peça pode capturar a peça que está dando xeque
        //se nenhuma peça puder capturar a peça que está dando xeque, é xeque mate
        Posicao posicaoRei = (corJogadorAtual == Cor.BRANCO) ? reiBrancoPosicao : reiPretoPosicao;
        System.out.println("Cor do Rei "+corJogadorAtual);
        System.out.println("Posição do rei: " + posicaoRei);
        // Verifique se o rei está em xeque
        if (!verificarSeReiEstaEmXeque(posicaoRei, corJogadorAtual)) {
            return false;
        }
        // Simule todos os movimentos possíveis para verificar se é possível sair do xeque
        Tabuleiro simulacao;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Peca peca = tabuleiro[i][j];
                simulacao=this.clone();
                if (peca != null && peca.getCor() == corJogadorAtual) {
                    List<Posicao> movimentos = calcularMovimentosValidos(peca, simulacao);
                    for (Posicao movimento : movimentos) {
                        var previusPosicao = peca.getPosicao();
                        peca.movimentar(movimento,simulacao);
                        if (!this.verificarSeReiEstaEmXeque(posicaoRei, corJogadorAtual)) {
                            System.out.println("Previus pos: "+previusPosicao);
                            System.out.println("Movimento válido: "+movimento);
                            System.out.println(simulacao);
                            simulacao=null;
                            return false;
                        }
                    }
                }
            }
        }
        simulacao=null;
        return true;
    }

    public void promoverPeao(Posicao peaoPos,String tipo){
        Peca peao = tabuleiro[peaoPos.getLinha()][peaoPos.getColuna()];
        Peca novaPeca = null;
        switch (tipo){
            case "QUEEN":
                novaPeca = new Rainha(peao.getCor(),peao.getPosicao());
                break;
            case "TOWER":
                novaPeca = new Torre(peao.getCor(),peao.getPosicao());
                break;
            case "HORSE":
                novaPeca = new Cavalo(peao.getCor(),peao.getPosicao());
                break;
            case "BISHOP":
                novaPeca = new Bispo(peao.getCor(),peao.getPosicao());
                break;
        }
        tabuleiro[peao.getPosicao().getLinha()][peao.getPosicao().getColuna()] = novaPeca;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("   ");
        for (int j = 0; j < colunas; j++) {
            s.append(j).append("   ");
        }
        s.append("\n");
        for (int i = 0; i < linhas; i++) {
            s.append(i).append(" | ");
            for (int j = 0; j < colunas; j++) {
                if (tabuleiro[i][j] == null) {
                    s.append("   |"); // Empty space
                } else {
                    s.append(tabuleiro[i][j].toString()).append(" |"); // Piece representation
                }
            }
    
            s.append("\n");
        }
        return s.toString();
    }
    
    public Peca getPeca(Posicao posicao){
        return tabuleiro[posicao.getLinha()][posicao.getColuna()];
    }
}
