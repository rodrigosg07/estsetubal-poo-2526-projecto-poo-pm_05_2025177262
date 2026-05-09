public class Nivel {
    private int numero;
    private int maxTentativas;
    private int linhas;
    private int colunas;

    public Nivel(int numero, int maxTentativas, int linhas, int colunas) {
        if (linhas <= 0 || colunas <= 0) throw new IllegalArgumentException("Dimensões de tabuleiro inválidas");
        if (linhas * colunas % 2 != 0 ) throw new IllegalArgumentException("Dimensões de tabuleiro inválidas");
        if (maxTentativas <= 0) throw new IllegalArgumentException("O maximo de tentativas deve ser positivo");
        this.numero = numero;
        this.maxTentativas = maxTentativas;
        this.linhas = linhas;
        this.colunas = colunas;
    }

    public int getNumero() {
        return numero;
    }

    public int getMaxTentativas() {
        return maxTentativas;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

}
