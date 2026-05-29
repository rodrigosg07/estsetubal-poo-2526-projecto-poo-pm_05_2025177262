public class CartaRevelar extends CartaEspecial {
    public CartaRevelar(int id, String simbolo){
        super(id, simbolo);
    }

    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.avisarPoderAtivado("Revelar 1 carta à escolha!");

        for (Carta c : jogo.getTabuleiro().getCartas()) {
            if (c.getEstado() == EstadoCarta.VIRADA_BAIXO) {
                try {
                    c.virar();
                } catch (JogoException ignored) {
                    break;
                }
            }
        }
    }
}
