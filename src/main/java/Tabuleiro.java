import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {
    private Carta primeiraCartaSelecionada = null;
    private Nivel nivel;
    private List<Carta> cartas;

    public Tabuleiro(Nivel nivelAtual) {
        this.nivel = nivelAtual;
        this.cartas = new ArrayList<>();
    }

    public void inicializar() {
        cartas.clear();
        int numLinhas = nivel.getLinhas();
        int numColunas = nivel.getColunas();
        int totalCartas = numLinhas * numColunas;
        int totalPares = totalCartas / 2;
        int proximoid = cartas.size();
        List<String> simbolos = new ArrayList<>(Arrays.asList("Gato", "Cão", "Passaro", "Cobra", "Peixe", "Macaco", "Girafa", "Leão", "Crocodilo", "Tigre", "Cabra", "Vaca", "Porco", "Sapo", "Tartaruga", "Flamingo", "Rato", "Tubarão"));
        Collections.shuffle(simbolos);

        for (int i = 0; i < totalPares - 1; i++) {
            cartas.add(new CartaNormal(i * 2, simbolos.get(i)));
            cartas.add(new CartaNormal(i * 2 + 1, simbolos.get(i)));
        }
        cartas.add(new CartaEspecial(proximoid, "Elefante"));
        cartas.add(new CartaEspecial(proximoid + 1, "Elefante"));
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public List<Carta> getCartas() {
        return this.cartas;
    }

    public Carta getCarta(int posicao) {
        return cartas.get(posicao);
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}


