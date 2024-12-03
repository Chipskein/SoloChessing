package Game;

public enum PromocaoPeaoTipo {
    RAINHA,TORRE,BISPO,CAVALO;
    public static PromocaoPeaoTipo fromString(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
    
        switch (valor.toUpperCase()) {
            case "QUEEN":
                return RAINHA;
            case "TOWER":
                return TORRE;
            case "BISHOP":
                return BISPO;
            case "HORSE":
                return CAVALO;
            default:
                throw new IllegalArgumentException("Valor inválido: " + valor);
        }
    }
}
 
