public class CartaRevelar extends CartaEspecial {
    public CartaRevelar(int id, String simbolo){
        super(id, simbolo);
    }

    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.avisarPoderAtivado("Revelar 1 carta à escolha!");
        jogo.setModoRevelarEscolha(true);
    }
}
