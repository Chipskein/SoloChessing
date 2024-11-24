package Game.Peca;

import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
public abstract class Peca {
    protected Cor cor;
    protected Posicao posicao;
    protected boolean capturada = false;
    public Peca(Cor cor, Posicao posicao) {
        this.cor = cor;
        this.posicao = posicao;
    }

    public  boolean movimentoValido(Posicao destino, Tabuleiro tabuleiro){
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
    
    public boolean verificarTrajetoriaDiagonal(Posicao destino, Tabuleiro tabuleiro){
        int passoX = (destino.getLinha() > this.posicao.getLinha()) ? 1 : -1;
        int passoY = (destino.getColuna() > this.posicao.getColuna()) ? 1 : -1;
        // Percorre as casas entre a posição atual e a posição de destino
        int x = this.posicao.getLinha() + passoX;
        int y = this.posicao.getColuna() + passoY;
        while (x != destino.getLinha() && y != destino.getColuna()) {
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
    public boolean verificarTrajetoriaRetilinea(Posicao destino, Tabuleiro tabuleiro){
        int passoX = 0, passoY = 0;
        if (destino.getLinha() > this.posicao.getLinha()) passoX = 1;
        else if (destino.getLinha() < this.posicao.getLinha()) passoX = -1;
        if (destino.getColuna() > this.posicao.getColuna()) passoY = 1;
        else if (destino.getColuna() < this.posicao.getColuna()) passoY = -1;
        // Percorre as casas entre a posição atual e a posição de destino
        int x = this.posicao.getLinha() + passoX;
        int y = this.posicao.getColuna() + passoY;
        while (x != destino.getLinha() || y != destino.getColuna()) {
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

    public void movimentar(Posicao destino, Tabuleiro tabuleiro){
        var dest=tabuleiro.getTabuleiro()[destino.getLinha()][destino.getColuna()];
        if(dest!=null){
            dest.capturada=true;
        }
        tabuleiro.getTabuleiro()[destino.getLinha()][destino.getColuna()]=this;
        tabuleiro.getTabuleiro()[this.posicao.getLinha()][this.posicao.getColuna()]=null;
        // Atualiza a posição do rei caso a peça movida seja um rei
        if(this.posicao.equals(tabuleiro.getReiBrancoPosicao())) tabuleiro.setReiBrancoPosicao(destino);
        if(this.posicao.equals(tabuleiro.getReiPretoPosicao())) tabuleiro.setReiPretoPosicao(destino);
        this.posicao=destino;
    }
    public Cor getCor(){
        return cor;
    };    

}
