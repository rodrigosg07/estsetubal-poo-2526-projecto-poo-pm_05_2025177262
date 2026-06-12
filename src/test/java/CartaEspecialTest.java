import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class CartaEspecialTest {
    private Jogo jogo;
    private Jogador jogador;
    private Tabuleiro tabuleiro;
    private final Nivel nivel;

    public CartaEspecialTest(Nivel nivel) {
        this.nivel = nivel;
    }

    @BeforeEach
    void setUp(){
        jogador = new Jogador("A", 0, 1);
        tabuleiro = new Tabuleiro(nivel);
        jogo = new Jogo(jogador);

    }

    @Test
    void testeCartaTentativaExtra() {
        CartaTentativaExtra c1 = new CartaTentativaExtra(1, "1");

        int pontosInciais = jogador.getPontuacao();
        int tentativasInciais = jogador.getTentativas();

        c1.ativar(jogo);

        assertEquals(pontosInciais + 20, jogador.getPontuacao());
        assertEquals(tentativasInciais + 1, jogador.getTentativas());
    }

    @Test
    void testeCartaRevelar() {
        CartaRevelar c2 = new CartaRevelar(2, "2");

        Carta carta1 = new Carta(21, "21") {};
        Carta carta2 = new Carta(22, "22") {};

        carta2.emparelhar();
        jogo.getTabuleiro().setCartas(Arrays.asList(carta1, carta2));
        int pontosIniciais = jogador.getPontuacao();

        c2.ativar(jogo);

        assertEquals(pontosIniciais + 20, jogador.getPontuacao());
        assertEquals(EstadoCarta.VIRADA_CIMA, carta1.getEstado());
        assertEquals(EstadoCarta.EMPARELHADA, carta2.getEstado());
    }

    @Test
    void testeCartaRevelarPar() {
        CartaRevelarPar c3 = new CartaRevelarPar(3, "3");

        Carta carta3 = new CartaNormal(31, "31") {};
        Carta carta4 = new CartaNormal(32, "32") {};
        Carta carta5 = new CartaNormal(33, "31") {};

        jogo.getTabuleiro().setCartas(Arrays.asList(carta3, carta4, carta5));
        int pontosIniciais = jogador.getPontuacao();
        int paresIniciais = jogo.getParesEncontrados();

        c3.ativar(jogo);

        assertEquals(pontosIniciais + 20, jogador.getPontuacao());
        assertEquals(EstadoCarta.EMPARELHADA, carta3.getEstado());
        assertEquals(EstadoCarta.EMPARELHADA, carta5.getEstado());
        assertEquals(EstadoCarta.VIRADA_BAIXO, carta4.getEstado());
        assertEquals(paresIniciais + 1, jogo.getParesEncontrados());
    }

    @Test
    void testeCartaTrocarPosicao() {
        CartaTrocarPosicao c4 = new CartaTrocarPosicao(4, "4");

        Carta carta6 = new Carta(41, "41") {};
        Carta carta7 = new Carta(42, "42") {};
        carta7.emparelhar();
        Carta carta8 = new Carta(43, "43") {};

        List<Carta> listaTabuleiro = Arrays.asList(carta6, carta7, carta8);
        tabuleiro.setCartas(listaTabuleiro);

        int pontosIniciais = jogador.getPontuacao();

        c4.ativar(jogo);

        List<Carta> cartasAposAtivacao = tabuleiro.getCartas();

        assertEquals(pontosIniciais + 20, jogador.getPontuacao());
        //A carta carta7 (EMPARELHADA) DEVE continuar no índice 1
        assertEquals(carta7, cartasAposAtivacao.get(1));
        // As posições 0 e 2 continham carta6 e carta8. O teste garante que nenhuma carta foi perdida
        assertTrue(cartasAposAtivacao.contains(carta6));
        assertTrue(cartasAposAtivacao.contains(carta8));
    }

}
