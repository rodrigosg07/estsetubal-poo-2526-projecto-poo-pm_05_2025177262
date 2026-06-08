import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class testCarta {
    private Carta carta;

    @BeforeEach
    void setUp(){
        // Criação de uma instância anónima para testar a classe abstrata
        carta = new Carta(1, "A"){};
    }

    @Test
    void testeDeInicializacaoCorreta() {
        assertEquals(1, carta.getId());
        assertEquals("A", carta.getSimbolo());
        assertEquals(EstadoCarta.VIRADA_BAIXO, carta.getEstado());
    }

    @Test
    void testeValidarSimbolo(){
        assertThrows(IllegalAccessException.class, () -> new Carta(2, null) {});
        assertThrows(IllegalAccessException.class, () -> new Carta(3, " ") {});
    }

    @Test
    void testeAlterarEstadoAoVirar(){
        // selecionar carta virada para baixo
        carta.virar();
        assertEquals(EstadoCarta.VIRADA_CIMA, carta.getEstado());

        // carta selecionada não emparelha -> volta a virar para baixo
        carta.virar();
        assertEquals(EstadoCarta.VIRADA_BAIXO, carta.getEstado());
    }

    @Test
    void testeDeveEmparelharCarta() {
        carta.emparelhar();
        assertEquals(EstadoCarta.EMPARELHADA, carta.getEstado());
    }


    @Test
    void testeAdiconarPontosNaAtivacaoDaCartaNormal(){
        Jogador jogador1 = new Jogador("A", 0, 1);
        Jogo jogo1 = new Jogo(jogador1);

        int pontosAntes = jogo1.getJogador().getPontuacao();
        carta.ativar(jogo1);
        int pontosDepois = jogo1.getJogador().getPontuacao();

        assertEquals(pontosAntes + 10, pontosDepois, "O jogador devia ter recebido 10 pontos");

    }

}
