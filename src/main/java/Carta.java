/**
 * Classe abstrata que serve de base lógica para todas as cartas em jogo.
 * Encapsula as propriedades fundamentais (id e símbolo) e gere o estado visual
 * da carta (virada, escondida ou emparelhada).
 */
public abstract class Carta {
    private final int id;
    private final String simbolo;
    private EstadoCarta estado;

    /**
     * Construtor protegido para ser acedido apenas por esta classe e pelas suas subclasses.
     * Inicializa a carta garantindo que tem um símbolo válido e define o seu estado
     * inicial como virada para baixo.
     *
     * @param id O identificador único da carta.
     * @param simbolo O nome ou representação em texto do animal/símbolo da carta.
     * @throws SimboloException se o símbolo passado for nulo ou estiver em branco.
     */
    protected Carta(int id, String simbolo){
        if (simbolo == null || simbolo.isBlank()){
            throw new SimboloException("O símbolo não pode estar vazio");
        }
        this.id = id;
        this.simbolo = simbolo;
        this.estado = EstadoCarta.VIRADA_BAIXO;
    }

    /**
     * Altera o estado da carta selecionada.
     * Se estiver virada para baixo, vira-a para cima. Se estiver para cima, volta a escondê-la.
     * (Não afeta cartas que já estejam no estado EMPARELHADA).
     */
    public void virar(){
        if (estado == EstadoCarta.VIRADA_BAIXO){
            estado = EstadoCarta.VIRADA_CIMA;
        }else if(estado == EstadoCarta.VIRADA_CIMA){
            estado = EstadoCarta.VIRADA_BAIXO;
        }
    }

    /**
     * Bloqueia a carta, marcando-a permanentemente como parte de um par já encontrado.
     */
    public void emparelhar(){
        estado = EstadoCarta.EMPARELHADA;
    }

    public int getId(){
        return id;
    }

    public String getSimbolo(){
        return simbolo;
    }

    public EstadoCarta getEstado(){
        return estado;
    }

    /**
     * Define a ação base acionada quando o jogador encontra o par desta carta.
     * Pode (e deve) sofrer override nas subclasses para implementar poderes específicos.
     *
     * @param jogo A instância atual do jogo, permitindo manipular pontuações, tentativas ou tabuleiro.
     */
    public void ativar(Jogo jogo){
        jogo.getJogador().adicionarPontos(10);
    }
}
