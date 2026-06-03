import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogoExceptionTest {
    @Test
    void testeDeveMostrarMensagemDeErro() {
        String mensagemErro = "Ação inválida no tabuleiro!";
        JogoException jogoException = new JogoException(mensagemErro);

        assertEquals(mensagemErro, jogoException.getMessage());
    }
}
