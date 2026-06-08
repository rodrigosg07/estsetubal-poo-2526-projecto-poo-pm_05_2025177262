import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JogoTest {
    private Jogo jogo;
    private Jogador jogador;

    @BeforeEach
    void setUp() {
        jogador = new Jogador("A", 0, 25);
        jogo = new Jogo(jogador);
        jogo.mudarNivel(1);
    }

    @Test
    void testInicializacaoNivelUm() {
        assertEquals(1, jogo.getNivelAtual().getNumero());
        assertEquals(25, jogador.getTentativas(), "Nível 1 deve começar com 25 tentativas!");
        assertEquals(0, jogador.getPontuacao(), "A pontuação inicial deve ser 0!");
    }

    @Test
    void testEscolhaDePrimeiraCartaNaoConsomeTentativa() throws JogoException {
        Carta c1 = new CartaNormal(1, "A");
        jogo.getTabuleiro().getCartas().add(c1);

        boolean formouPar = jogo.escolherCarta(c1);

        assertFalse(formouPar, "A primeira carta de um turno nunca pode fechar um par!");
        assertEquals(EstadoCarta.VIRADA_CIMA, c1.getEstado());
        assertEquals(25, jogador.getTentativas(), "Tentativas não devem mudar no primeiro clique!");
    }

    @Test
    void testBloqueioDoTabuleiroDuranteTurnoIncorreto() {
        Carta c1 = new CartaNormal(1, "A");
        Carta c2 = new CartaNormal(2, "B");

        jogo.escolherCarta(c1);
        jogo.escolherCarta(c2);

        assertTrue(jogo.isBloqueado(), "O jogo deve bloquear cliques enquanto exibe o par incorreto!");
    }

    @Test
    void testePossuiComponentesValidos(){
        assertNotNull(jogo.getJogador(), "O jogo deve ter um jogador associado");
        assertNotNull(jogo.getTabuleiro(), "O jogo deve ter um tabuleiro associado");
    }

    @Test
    void testeIncrementarParesEncontrados() {
        int paresIniciais = jogo.getParesEncontrados();

        jogo.incrementarParesEncontrados();

        assertEquals(paresIniciais + 1, jogo.getParesEncontrados());
    }
}
