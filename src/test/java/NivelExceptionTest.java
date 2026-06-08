import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NivelExceptionTest {
    @Test
    void testeDeveLancarExcecaoQuandoLinhasInvalidas() {
        // Testando com 0 linhas
        NivelException nivelException = assertThrows(NivelException.class, () -> {
            new Nivel(1,10,0,4);
        });

        assertEquals("Dimensões de tabuleiro inválidas", nivelException.getMessage());

        // Testando com linhas negativas
        NivelException nivelException1 = assertThrows(NivelException.class, () -> {
            new Nivel(1,10,-4,4);
        });

        assertEquals("Dimensões de tabuleiro inválidas", nivelException1.getMessage());
    }

    @Test
    void testeDeveLancarExcecaoQuandoColunasInvalidas() {
        // Testando com 0 colunas
        NivelException nivelException = assertThrows(NivelException.class, () -> {
            new Nivel(1,10,4,0);
        });

        assertEquals("Dimensões de tabuleiro inválidas", nivelException.getMessage());

        // Testando com colunas negativas
        NivelException nivelException1 = assertThrows(NivelException.class, () -> {
            new Nivel(1,10,4,-4);
        });

        assertEquals("Dimensões de tabuleiro inválidas", nivelException1.getMessage());
    }

    @Test
    void testeDeveLancarExcecaoQuandoTotalDeCartasForImpar() {
        NivelException nivelException = assertThrows(NivelException.class, () -> {
            new Nivel(1,10,3,3);
        });

        assertEquals("Dimensões de tabuleiro inválidas", nivelException.getMessage());
    }

    @Test
    void testeDeveLancarExcecaoQuandoMaxTentativasInvalido() {
        //Testar a 0 tentativas
        NivelException nivelException = assertThrows(NivelException.class, () -> {
            new Nivel(1,0,4,4);
        });

        assertEquals("O maximo de tentativas deve ser positivo", nivelException.getMessage());

        //Testar a tentativas negativas
        NivelException nivelException2 = assertThrows(NivelException.class, () -> {
            new Nivel(1,-1,4,4);
        });

        assertEquals("O maximo de tentativas deve ser positivo", nivelException2.getMessage());
    }
}
