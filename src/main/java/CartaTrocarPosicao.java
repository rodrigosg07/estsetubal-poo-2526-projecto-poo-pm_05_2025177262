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
        List<Carta> fechadas = new ArrayList<>(); // Lista temporária para guardar apenas as cartas fechadas(viradas para baixo)
        List<Integer> indices = new ArrayList<>(); // Guarda as posições dessas cartas no tabuleiro

        for (int i = 0; i < cartas.size(); i++){
            if (cartas.get(i).getEstado() == EstadoCarta.VIRADA_BAIXO){
                fechadas.add(cartas.get(i));
                indices.add(i);
            }
        }

        Collections.shuffle(fechadas);

        for (int i = 0; i < indices.size(); i++){
            cartas.set(indices.get(i), fechadas.get(i));
            // Troca a carta da posição original
            // pela carta embaralhada
        }
    }
}
