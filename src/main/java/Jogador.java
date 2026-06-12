/**
 * Representa o jogador na sessão atual do "Memória de Elefante".
 * É responsável por fazer a gestão do estado do utilizador, controlando a sua
 * pontuação acumulada e o número de tentativas restantes para concluir os níveis.
 */
public class Jogador{
    private int pontuacao;
    private int tentativas;

    /**
     * Constrói uma nova instância de Jogador com validações de integridade de dados.
     *
     * @param nomeJogador O nome identificativo do jogador.
     * @param pontuacao A pontuação inicial com que o jogador começa.
     * @param tentativas O número inicial de tentativas atribuídas.
     * @throws JogadorException se o nome for nulo, ou se a pontuação/tentativas forem negativas.
     */
    public Jogador(String nomeJogador, int pontuacao, int tentativas){
        if(nomeJogador==null) throw new JogadorException("Tem de colocar nome");
        if(pontuacao<0) throw new JogadorException("A pontuação não pode ser negativa");
        this.pontuacao=pontuacao;
        setTentativas(tentativas);
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        if(tentativas<0) throw new JogadorException("As tentativas não podem ser negativas");
        this.tentativas = tentativas;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    /**
     * Incrementa um determinado número de tentativas ao saldo atual do jogador.
     * Utilizado geralmente por bónus ou poderes especiais.
     *
     * @param t Número de tentativas a adicionar.
     */
    public void incrementarTentativas(int t){
        this.tentativas += t;
    }

    /**
     * Adiciona uma quantidade de pontos à pontuação acumulada do jogador.
     *
     * @param pontos Quantidade de pontos a somar.
     */
    public void adicionarPontos(int pontos){
        pontuacao+=pontos;
    }

    /**
     * Reduz numa unidade o número de tentativas disponíveis do jogador.
     * Executado a cada jogada falhada ou turno consumido.
     */
    public void consumirTentativa(){
        tentativas--;
    }

    /**
     * Reinicia o estado das tentativas do jogador para o valor neutro.
     */
    public void resetTentativas(){
        tentativas=0;
    }

    /**
     * Reinicia a pontuação acumulada do jogador, voltando a zero.
     */
    public void resetPontos(){
        this.pontuacao=0;
    }
}
