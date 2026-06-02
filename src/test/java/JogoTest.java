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
    void testExcecaoAoEscolherCartaJaVirada() throws JogoException {
        Carta c1 = new CartaNormal(1, "A");
        jogo.getTabuleiro().getCartas().add(c1);

        jogo.escolherCarta(c1);

        assertThrows(JogoException.class, () -> jogo.escolherCarta(c1), "Devia lançar JogoException ao clicar numa carta que não está VIRADA_BAIXO!");
    }

    @Test
    void testBloqueioDoTabuleiroDuranteTurnoIncorreto() throws JogoException {
        Carta c1 = new CartaNormal(1, "A");
        Carta c2 = new CartaNormal(2, "B");

        jogo.escolherCarta(c1);
        jogo.escolherCarta(c2);

        assertTrue(jogo.isBloqueado(), "O jogo deve bloquear cliques enquanto exibe o par incorreto!");
    }

    @Test
    void testPoderCartaTentativaExtraFunciona() throws JogoException {
        CartaTentativaExtra esp1 = new CartaTentativaExtra(1, "a");
        CartaTentativaExtra esp2 = new CartaTentativaExtra(2, "a");

        jogador.setTentativas(10);

        jogo.escolherCarta(esp1);
        jogo.escolherCarta(esp2);

        // 10 originais - 1 (do turno realizado) + 1 (do superpoder) = 10
        assertEquals(10, jogador.getTentativas(), "O poder deve adicionar +1 tentativa!");
        assertEquals(20, jogador.getPontuacao(), "Pares especiais devem garantir 20 pontos!");
        assertEquals(EstadoCarta.EMPARELHADA, esp1.getEstado());
    }

    @Test
    void testCartaNormalApenasPontuaPadrao() throws JogoException {
        CartaNormal cn1 = new CartaNormal(1, "b");
        CartaNormal cn2 = new CartaNormal(2, "b");

        jogador.setTentativas(10);

        jogo.escolherCarta(cn1);
        jogo.escolherCarta(cn2);

        assertEquals(9, jogador.getTentativas());
        assertEquals(10, jogador.getPontuacao(), "Par normal confere apenas 10 pontos!");
    }
}
