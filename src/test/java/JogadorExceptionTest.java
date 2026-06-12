import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JogadorExceptionTest {
    @Test
    void TesteDeveLancatExcecaoSeNomeDeJogadorForNull() {
        JogadorException jogadorException = assertThrows(JogadorException.class, () -> new Jogador(null, 1, 1));

        assertEquals("Tem de colocar nome", jogadorException.getMessage());
    }

    @Test
    void TesteDeveLancatExcecaoSePontuacaoNegativa() {
        JogadorException jogadorException2 = assertThrows(JogadorException.class, () -> new Jogador("a", -1, 1));

        assertEquals("A pontuação não pode ser negativa", jogadorException2.getMessage());
    }

    @Test
    void TesteDeveLancatExcecaoSeTentativasNegativas() {
        JogadorException jogadorException3 = assertThrows(JogadorException.class, () -> new Jogador("a", 1, -1));

        assertEquals("As tentativas não podem ser negativas", jogadorException3.getMessage());
    }

}
