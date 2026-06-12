import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TabuleiroTest {
    private final Nivel nivel;

    public TabuleiroTest(Nivel nivel) {
        this.nivel = nivel;
    }

    @Test
    void testeDeveIncializarComCartas(){
        Tabuleiro tabuleiro = new Tabuleiro(nivel);

        List<Carta> cartas = tabuleiro.getCartas();

        assertNotNull(cartas, "A lista de cartas não pode ser nula");
    }
}
