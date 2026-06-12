/**
 * Representa uma carta especial que, ao formar par, ativa o poder de espreitar
 * uma carta qualquer ainda virada para baixo no tabuleiro.
 * Estende a classe CartaEspecial, implementando um comportamento dinâmico
 * através do override do metodo de ativação.
 */
public class CartaRevelar extends CartaEspecial {
    public CartaRevelar(int id, String simbolo){
        super(id, simbolo);
    }

    /**
     * Executa o comportamento específico deste poder especial.
     * Bonifica o jogador com 20 pontos (em vez dos 10 padrão), notifica a "interface"
     * de que o poder foi disparado e altera o estado do jogo para o modo de revelação,
     * permitindo que o próximo clique do utilizador apenas espreite uma carta sem penalização.
     *
     * @param jogo A instância ativa do jogo onde as alterações de estado serão aplicadas.
     */
    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.avisarPoderAtivado("Revelar 1 carta à escolha!");
        jogo.setModoRevelarEscolha(true);
    }
}
