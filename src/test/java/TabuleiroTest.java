import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TabuleiroTest {
    private Nivel nivel;

    @Test
    void testeDeveIncializarComCartas(){
        Tabuleiro tabuleiro = new Tabuleiro(nivel);

        List<Carta> cartas = tabuleiro.getCartas();

        assertNotNull(cartas, "A lista de cartas não pode ser nula");
    }
}
