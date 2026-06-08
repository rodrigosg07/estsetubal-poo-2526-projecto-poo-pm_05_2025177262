import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JogadorTest {
    private Jogador jogador;

    @BeforeEach
    void setUp(){
        jogador = new Jogador("A", 0, 1);
    }

    @Test
    void testeAdicionarPontosCorretamente(){
        jogador.adicionarPontos(10);
        assertEquals(10, jogador.getPontuacao());

        jogador.adicionarPontos(20);
        assertEquals(30, jogador.getPontuacao());
    }

    @Test
    void testeIncrementarTentativasCorretamente(){
        int tentativasInicias = jogador.getTentativas();

        jogador.incrementarTentativas(1);
        assertEquals(tentativasInicias + 1, jogador.getTentativas());

        jogador.incrementarTentativas(2);
        assertEquals(tentativasInicias + 3, jogador.getTentativas());
    }
}
