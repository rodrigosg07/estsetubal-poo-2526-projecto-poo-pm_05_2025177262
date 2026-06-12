/**
 * Representa a configuração de um nível de jogo no "Memória de Elefante".
 * Define as propriedades estruturais da grelha (linhas e colunas) e as restrições
 * de jogabilidade, como o limite máximo de tentativas permitidas ao jogador.
 */
public class Nivel {
    private final int numero;
    private final int maxTentativas;
    private final int linhas;
    private final int colunas;

    /**
     * Constrói uma nova instância de um nível, aplicando regras estritas de validação
     * matemática para garantir a integridade da lógica do tabuleiro.
     *
     * @param numero O número identificador do nível (ex: 1, 2, 3...).
     * @param maxTentativas O número de falhas permitidas antes de ditar o game over.
     * @param linhas O número de linhas da grelha de cartas.
     * @param colunas O número de colunas da grelha de cartas.
     * @throws NivelException se as dimensões forem menores ou iguais a zero,
     * se o produto de linhas por colunas for ímpar (o que impossibilitaria a formação de pares),
     * ou se o número máximo de tentativas não for estritamente positivo.
     */
    public Nivel(int numero, int maxTentativas, int linhas, int colunas) {
        if (linhas <= 0 || colunas <= 0) throw new NivelException("Dimensões de tabuleiro inválidas");
        if (linhas * colunas % 2 != 0 ) throw new NivelException("Dimensões de tabuleiro inválidas");
        if (maxTentativas <= 0) throw new NivelException("O maximo de tentativas deve ser positivo");
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
