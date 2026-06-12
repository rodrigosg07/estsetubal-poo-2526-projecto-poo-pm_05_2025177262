import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa uma carta especial que, ao formar par, ativa o poder de baralhar
 * a posição de todas as cartas que ainda não foram descobertas no tabuleiro.
 * Estende a classe CartaEspecial, introduzindo um efeito mecânico que
 * reorganiza a disposição física da grelha para aumentar o desafio.
 */
public class CartaTrocarPosicao extends CartaEspecial {

    /**
     * Constrói uma nova CartaTrocarPosicao associando-lhe o seu id e símbolo.
     *
     * @param id O identificador único da carta na grelha.
     * @param simbolo O nome do símbolo da carta (geralmente "Elefante").
     */
    public CartaTrocarPosicao(int id, String simbolo){
        super(id, simbolo);
    }

    /**
     * Executa o comportamento específico deste poder especial através do *override* do metodo.
     * Atribui 20 pontos ao jogador, notifica a interface e recolhe todas as cartas cujo
     * estado seja diferente de EstadoCarta.EMPARELHADA, guardando também as suas
     * posições originais. Aplica um *shuffle* a essa lista de cartas ocultas e volta a
     * introduzi-las na lista global do tabuleiro nos mesmos índices, trocando a sua disposição
     * sem afetar os pares já resolvidos.
     *
     * @param jogo A instância ativa do jogo onde o rearranjo do tabuleiro será aplicado.
     */
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
