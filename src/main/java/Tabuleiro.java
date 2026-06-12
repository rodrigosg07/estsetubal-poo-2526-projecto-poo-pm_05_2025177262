import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representa o tabuleiro do jogo, sendo responsável por gerir o conjunto de cartas em jogo.
 * Controla a criação estruturada das cartas normais e a inclusão dinâmica de cartas
 * com poderes especiais, adaptando-se às dimensões especificadas por cada nível.
 */
public class Tabuleiro {
    private final Nivel nivel;
    private List<Carta> cartas;

    /**
     * Constrói uma nova instância do tabuleiro associada a um nível específico.
     *
     * @param nivelAtual O nível de jogo que dita o número de linhas e colunas desta grelha.
     */
    public Tabuleiro(Nivel nivelAtual) {
        this.nivel = nivelAtual;
        this.cartas = new ArrayList<>();
    }

    /**
     * Inicializa o tabuleiro gerando a lista de cartas necessária para preencher o ecrã.
     * O metodo limpa o estado anterior, seleciona e baralha os símbolos dos animais para os
     * pares normais e introduz obrigatoriamente um par de cartas especiais ("Elefante")
     * cujo poder (Revelar, Revelar Par, Tentativa Extra ou Trocar Posição) é escolhido de forma aleatória.
     */
    public void inicializar() {
        cartas.clear();
        int numLinhas = nivel.getLinhas();
        int numColunas = nivel.getColunas();
        int totalCartas = numLinhas * numColunas;
        int totalPares = totalCartas / 2;
        int proximoid = cartas.size();
        List<String> simbolos = new ArrayList<>(Arrays.asList("Gato", "Cão", "Passaro", "Cobra", "Peixe", "Macaco", "Girafa", "Leão",
                "Crocodilo", "Tigre", "Cabra", "Vaca", "Porco", "Sapo", "Tartaruga", "Flamingo", "Rato", "Tubarão"));
        Collections.shuffle(simbolos);

        for (int i = 0; i < totalPares - 1; i++) {
            cartas.add(new CartaNormal(i * 2, simbolos.get(i)));
            cartas.add(new CartaNormal(i * 2 + 1, simbolos.get(i)));
        }
        int randomPoder = (int) (Math.random() * 4);

        switch (randomPoder) {
            case 0 -> {
                cartas.add(new CartaRevelar(proximoid, "Elefante"));
                cartas.add(new CartaRevelar(proximoid + 1, "Elefante"));
            }
            case 1 -> {
                cartas.add(new CartaRevelarPar(proximoid, "Elefante"));
                cartas.add(new CartaRevelarPar(proximoid + 1, "Elefante"));
            }
            case 2 -> {
                cartas.add(new CartaTentativaExtra(proximoid, "Elefante"));
                cartas.add(new CartaTentativaExtra(proximoid + 1, "Elefante"));
            }
            default -> {
                cartas.add(new CartaTrocarPosicao(proximoid, "Elefante"));
                cartas.add(new CartaTrocarPosicao(proximoid + 1, "Elefante"));
            }
        }
    }

    /**
     * Baralha aleatoriamente a disposição física das cartas na lista, garantindo
     * que as posições dos pares fiquem imprevisíveis a cada nova partida.
     */
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public List<Carta> getCartas() {
        return this.cartas;
    }

    public void setCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }
}


