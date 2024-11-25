import Game.Partida;
import Game.Posicao;
public class MainTestes {
    public static void main(String[] args) throws Exception {
        //Testes de movimentação de peças
        System.out.println("Testes de movimentação de peças");
        Partida partida = new Partida();
        //iniciarJogo com false para não iniciar o game loop
        partida.iniciarJogo();
        System.out.println("Tabuleiro inicial");
        System.out.println(partida.getTabuleiro());
        System.out.println("Turno 1");
        //turno 1 branco
        
        var teste=partida.getTabuleiro().getTabuleiro()[6][0].movimentoValido(new Posicao(4,0), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[6][0] linha para [4][0] 2 casas a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[6][0].movimentar(new Posicao(4,0), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 2 preto
        System.out.println("Turno 2");
        teste=partida.getTabuleiro().getTabuleiro()[1][0].movimentoValido(new Posicao(3,0), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[1][0] linha para [3][0] 2 casas a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[1][0].movimentar(new Posicao(3,0), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();
        //turno 3 branco
        System.out.println("Turno 3");
        teste=partida.getTabuleiro().getTabuleiro()[6][1].movimentoValido(new Posicao(4,1), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[6][1] linha para [4][1] 2 casas a frente "+teste);
        partida.getTabuleiro().getTabuleiro()[6][1].movimentar(new Posicao(4,1), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 4 preto
        System.out.println("Turno 4");
        teste=partida.getTabuleiro().getTabuleiro()[1][2].movimentoValido(new Posicao(3,2), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[1][2] linha para [3][2] 2 casas a frente "+teste);
        partida.getTabuleiro().getTabuleiro()[1][2].movimentar(new Posicao(3,2), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 5 branco
        System.out.println("Turno 5");
        teste=partida.getTabuleiro().getTabuleiro()[4][1].movimentoValido(new Posicao(3,0), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[4][1] linha para [3][0] diagonal testando captura do peao "+teste);
        partida.getTabuleiro().getTabuleiro()[4][1].movimentar(new Posicao(3,0), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 6 preto
        System.out.println("Turno 6");
        teste=partida.getTabuleiro().getTabuleiro()[0][0].movimentoValido(new Posicao(3,0), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Torre[0][0] linha para [3][0] Testando captura de peão "+teste);
        partida.getTabuleiro().getTabuleiro()[0][0].movimentar(new Posicao(3,0), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 7 branco
        System.out.println("Turno 7");
        teste=partida.getTabuleiro().getTabuleiro()[7][1].movimentoValido(new Posicao(5,2), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Cavalo[7][1] linha para [5][2] "+teste);
        partida.getTabuleiro().getTabuleiro()[7][1].movimentar(new Posicao(5,2), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 8 preto
        System.out.println("Turno 8");
        teste=partida.getTabuleiro().getTabuleiro()[1][3].movimentoValido(new Posicao(3,3), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Peao[1][3] linha para [3][3] "+teste);
        partida.getTabuleiro().getTabuleiro()[1][3].movimentar(new Posicao(3,3), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 9 branco
        System.out.println("Turno 9");
        teste=partida.getTabuleiro().getTabuleiro()[5][2].movimentoValido(new Posicao(3,3), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Cavalo[5][2] linha para [3][3] Testando captura de peão "+teste);
        partida.getTabuleiro().getTabuleiro()[5][2].movimentar(new Posicao(3,3), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 10 preto
        System.out.println("Turno 10");
        teste=partida.getTabuleiro().getTabuleiro()[0][2].movimentoValido(new Posicao(4,6), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Bispo[0][2] linha para [4][6] "+teste);
        partida.getTabuleiro().getTabuleiro()[0][2].movimentar(new Posicao(4,6), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 11 branco
        System.out.println("Turno 11");
        teste=partida.getTabuleiro().getTabuleiro()[7][6].movimentoValido(new Posicao(5,5), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Cavalo[7][6] linha para [5][5] "+teste);
        partida.getTabuleiro().getTabuleiro()[7][6].movimentar(new Posicao(5,5), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();
        //turno 12 preto
        System.out.println("Turno 12");
        teste=partida.getTabuleiro().getTabuleiro()[4][6].movimentoValido(new Posicao(5,5), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Bispo[4][6] linha para [5][5] Testando captura de cavalo "+teste);
        partida.getTabuleiro().getTabuleiro()[4][6].movimentar(new Posicao(5,5), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 13 branco
        System.out.println("Turno 13");
        teste=partida.getTabuleiro().getTabuleiro()[6][4].movimentoValido(new Posicao(5,5), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Peao[6][4] linha para [5][5] Testando captura de bispo "+teste);
        partida.getTabuleiro().getTabuleiro()[6][4].movimentar(new Posicao(5,5), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 14 preto
        System.out.println("Turno 14");
        teste=partida.getTabuleiro().getTabuleiro()[0][3].movimentoValido(new Posicao(2,1), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Rainha[0][3] linha para [2][1] "+teste);
        partida.getTabuleiro().getTabuleiro()[0][3].movimentar(new Posicao(2,1), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 15 branco
        System.out.println("Turno 15");
        teste=partida.getTabuleiro().getTabuleiro()[7][3].movimentoValido(new Posicao(6,4), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Rei[7][4] linha para [6][4] "+teste);
        partida.getTabuleiro().getTabuleiro()[7][4].movimentar(new Posicao(6,4), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        System.out.println("Fim dos testes de movimentação de peças");
        
        //Testes de Xeque-Mate
        System.out.println("Testes de Xeque-Mate");
        partida = new Partida();
        partida.iniciarJogo();
        System.out.println("Tabuleiro inicial");
        System.out.println(partida.getTabuleiro());
        System.out.println("Mate do louco");
        System.out.println("Turno 1");
        //turno 1 branco
        teste=partida.getTabuleiro().getTabuleiro()[6][0].movimentoValido(new Posicao(4,0), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[6][6] linha para [4][6] 2 casas a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[6][6].movimentar(new Posicao(4,6), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();
        
        //turno 2 preto
        System.out.println("Turno 2");
        teste=partida.getTabuleiro().getTabuleiro()[1][4].movimentoValido(new Posicao(2,4), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[1][4] linha para [2][4] 1 casa a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[1][4].movimentar(new Posicao(2,4), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();

        //turno 3 branco
        System.out.println("Turno 3");
        teste=partida.getTabuleiro().getTabuleiro()[6][5].movimentoValido(new Posicao(5,5), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo peão[6][5] linha para [5][5] 1 casa a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[6][5].movimentar(new Posicao(5,5), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();
        //turno 4 preto (xeque)
        System.out.println("Turno 4");
        teste=partida.getTabuleiro().getTabuleiro()[0][3].movimentoValido(new Posicao(4,7), partida.getTabuleiro());
        if(!teste){
            throw new Exception("Teste Fail");
        }
        System.out.println("Movendo Rainha[0][3] linha para [4][7] 2 casas a frente " + teste);
        partida.getTabuleiro().getTabuleiro()[0][3].movimentar(new Posicao(4,7), partida.getTabuleiro());
        System.out.println(partida.getTabuleiro());
        partida.mudarTurno();
        System.out.println("Fim dos testes de Xeque-Mate " +partida.isFimDeJogo());
    }    
}
