/**
 * Representa uma carta especial que, ao formar par, concede ao jogador
 * tentativas adicionais para evitar o game over.
 * Estende a classe CartaEspecial, adaptando dinamicamente o bónus
 * de tentativas atribuído com base no nível atual em que o utilizador se encontra.
 */
public class CartaTentativaExtra extends CartaEspecial {

    /**
     * Constrói uma nova CartaTentativaExtra associando-lhe o seu "id" e símbolo.
     *
     * @param id O identificador único da carta na grelha.
     * @param simbolo O nome do símbolo da carta (geralmente "Elefante").
     */
    public CartaTentativaExtra(int id, String simbolo){
        super(id, simbolo);
    }

    /**
     * Executa o comportamento específico deste poder especial.
     * Bonifica o jogador com 20 pontos e verifica o nível atual: se for o Nível 5
     * (o nível máximo), adiciona 3 tentativas ao saldo do jogador; caso contrário,
     * adiciona 2 tentativas. Atualiza o estado do jogador e regista a mensagem do
     * poder que foi ativado para ser consumida pela "interface".
     *
     * @param jogo A instância ativa do jogo que fornece o contexto do nível atual e o acesso ao jogador.
     */
    @Override
    public void ativar(Jogo jogo) {
        int nivelAtual = jogo.getNivelAtual().getNumero();
        int tentativasAAdicionar = (nivelAtual == 5) ? 3 : 2;
        jogo.getJogador().adicionarPontos(20);
        jogo.getJogador().incrementarTentativas(tentativasAAdicionar);
        jogo.avisarPoderAtivado("Ganhou +" + tentativasAAdicionar + " tentativas extra!");
    }
}
