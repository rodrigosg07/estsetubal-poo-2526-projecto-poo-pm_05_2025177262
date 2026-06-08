import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimboloExceptionTest {
    @Test
    void TesteDeveLancatExcecaoQuandoSimboloForNull() {
        SimboloException simboloException = assertThrows(SimboloException.class, () -> {
            new CartaNormal(1,null);
        });

        assertEquals("O símbolo não pode estar vazio", simboloException.getMessage());
    }

    @Test
    void TesteDeveLancatExcecaoQuandoSimboloForVazio() {
        SimboloException simboloException2 = assertThrows(SimboloException.class, () -> {
            new CartaNormal(1,"");
        });

        assertEquals("O símbolo não pode estar vazio", simboloException2.getMessage());
    }
}
