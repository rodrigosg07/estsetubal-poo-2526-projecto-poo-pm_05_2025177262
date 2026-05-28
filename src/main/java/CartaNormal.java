public class CartaNormal extends Carta {
    public CartaNormal(int id, String simbolo) {
        super(id, simbolo);
    }

    public void ativar(Jogo jogo) {
        // Carta normal apenas pontua o valor base, sem poderes extra.
        jogo.getJogador().adicionarPontos(10);
    }
}
