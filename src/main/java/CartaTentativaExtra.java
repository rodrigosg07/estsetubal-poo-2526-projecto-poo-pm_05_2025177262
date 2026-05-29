public class CartaTentativaExtra extends CartaEspecial {
    public CartaTentativaExtra(int id, String simbolo){
        super(id, simbolo);
    }

    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.getJogador().incrementarTentativas(1);
        jogo.avisarPoderAtivado("Tentativa extra!");
    }
}
