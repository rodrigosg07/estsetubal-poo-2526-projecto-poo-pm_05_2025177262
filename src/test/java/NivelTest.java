import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NivelTest {
    @Test
    void testeDeveCriarNivelValido(){
        Nivel nivel = new Nivel(1, 25, 4, 4);

        assertEquals(1, nivel.getNumero(), "O número do nível deve ser 1");
        assertEquals(25, nivel.getMaxTentativas(), "As tentativas devem ser 25");
        assertEquals(4, nivel.getLinhas(), "As linhas devem ser 4");
        assertEquals(4, nivel.getColunas(), "As colunas devem ser 4");
    }
}
