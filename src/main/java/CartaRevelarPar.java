/**
 * Representa uma carta especial que, ao formar par, ativa o poder de encontrar
 * e resolver automaticamente um par completo de cartas que ainda esteja oculto no tabuleiro.
 * Estende a classe CartaEspecial, implementando uma lógica de varrimento e
 * emparelhamento imediato.
 */
public class CartaRevelarPar extends CartaEspecial {

    /**
     * Constrói uma nova CartaRevelarPar associando-lhe o seu id e símbolo.
     *
     * @param id O identificador único da carta na grelha.
     * @param simbolo O nome do símbolo da carta (geralmente "Elefante").
     */
    public CartaRevelarPar(int id, String simbolo){
        super(id, simbolo);
    }

    /**
     * Executa o comportamento específico deste poder especial.
     * Atribui 20 pontos ao jogador e procura a primeira carta que esteja com o estado
     * EstadoCarta.VIRADA_BAIXO. Se encontrar, guarda o seu símbolo, localiza a sua
     * respetiva alma gémea no tabuleiro e força ambas a transitar diretamente para o estado
     * EMPARELHADA, incrementando os pares encontrados no jogo de forma automática.
     *
     * @param jogo A instância ativa do jogo onde o par será injetado e processado.
     */
    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.avisarPoderAtivado("Revelar PAR de cartas!");

        Carta alvo = null;
        for (Carta c : jogo.getTabuleiro().getCartas()) {
            if (c.getEstado() == EstadoCarta.VIRADA_BAIXO) {
                alvo = c;
                break;
            }
        }

        if (alvo != null){
            String simboloAlvo = alvo.getSimbolo();
            for(Carta c : jogo.getTabuleiro().getCartas()) {
                if (c.getSimbolo().equals(simboloAlvo)) {
                    c.emparelhar();
                }
            }
            jogo.incrementarParesEncontrados();
        }
    }
}
