public class CartaRevelarPar extends Carta{
    public CartaRevelarPar(int id, String simbolo){
        super(id, simbolo);
    }

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
