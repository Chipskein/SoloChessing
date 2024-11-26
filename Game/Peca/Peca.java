package Game.Peca;

import Game.Posicao;
import Game.Tabuleiro;
import Game.Cor;
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
            // Cria uma cópia superficial da peça
            Peca clone = (Peca) super.clone();
            // Se a posição ou cor forem objetos mutáveis, clone-os
            clone.posicao = new Posicao(this.posicao.getLinha(), this.posicao.getColuna());
            // Não é necessário clonar 'cor' se for um valor simples (não mutável)
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null; // Em caso de falha
        }
    }
  
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

    public Posicao getPosicao(){
        return posicao;
    }

    public String getSpritePath(){
        return spritePath;
    }
}
