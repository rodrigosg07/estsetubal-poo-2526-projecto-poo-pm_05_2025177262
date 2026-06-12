public class CartaTentativaExtra extends CartaEspecial {
    public CartaTentativaExtra(int id, String simbolo){
        super(id, simbolo);
    }

    @Override
    public void ativar(Jogo jogo) {
        int nivelAtual = jogo.getNivelAtual().getNumero();
        int tentativasAAdicionar = (nivelAtual == 5) ? 3 : 2;
        jogo.getJogador().adicionarPontos(20);
        jogo.getJogador().incrementarTentativas(tentativasAAdicionar);
        jogo.avisarPoderAtivado("Ganhou +" + tentativasAAdicionar + " tentativas extra!");
    }
}
