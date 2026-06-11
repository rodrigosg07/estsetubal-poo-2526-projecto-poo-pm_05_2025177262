import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartaTrocarPosicao extends CartaEspecial {
    public CartaTrocarPosicao(int id, String simbolo){
        super(id, simbolo);
    }

    @Override
    public void ativar(Jogo jogo) {
        jogo.getJogador().adicionarPontos(20);
        jogo.avisarPoderAtivado("Trocar posição de cartas!");

        List<Carta> cartas = jogo.getTabuleiro().getCartas();
        List<Carta> naoEncontradas = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < cartas.size(); i++){
            if (cartas.get(i).getEstado() != EstadoCarta.EMPARELHADA){
                naoEncontradas.add(cartas.get(i));
                indices.add(i);
            }
        }
        Collections.shuffle(naoEncontradas);

        for (int i = 0; i < indices.size(); i++){
            cartas.set(indices.get(i), naoEncontradas.get(i));
        }
    }
}
