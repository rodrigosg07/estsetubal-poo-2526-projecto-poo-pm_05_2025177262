/**
 * Representa uma carta convencional no tabuleiro do jogo "Memória de Elefante".
 * Esta classe é utilizada para os pares de animais comuns (como Cão, Gato, etc.)
 * que não possuem poderes especiais associados, executando apenas o comportamento
 * e a atribuição de pontuação padrão do jogo ao ser emparelhada.
 */
public class CartaNormal extends Carta {
    public CartaNormal(int id, String simbolo) {
        super(id, simbolo);
    }
}
