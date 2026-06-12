/**
 * Classe responsável por gerir a lógica de negócio do jogo "Memória de Elefante".
 * Controla o estado atual do nível, o fluxo de jogadas, a validação de pares,
 * a gestão de tentativas do jogador e a ativação de poderes especiais.
 */
public class Jogo {
    private final Jogador jogador;
    private Tabuleiro tabuleiro;
    private Nivel nivelAtual;
    private Carta primeiraSelecionada;
    private int paresEncontrados;
    private boolean bloqueado;
    private String poderAtivado;
    private boolean modoRevelarEscolha;

    /**
     * Constrói uma nova instância do jogo associada a um jogador.
     * Inicia automaticamente no Nível 1.
     * * @param jogador O jogador que irá realizar a sessão de jogo.
     */
    public Jogo(Jogador jogador){
        this.jogador = jogador;
        mudarNivel(1);
    }

    // --- Getters ---
    public Jogador getJogador() {
        return jogador;
    }
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public Nivel getNivelAtual() {
        return nivelAtual;
    }
    public Carta getPrimeiraSelecionada() {
        return primeiraSelecionada;
    }
    public String getPoderAtivado() { return poderAtivado; }
    public boolean isBloqueado() { return bloqueado; }
    public int getParesEncontrados() { return paresEncontrados; }

    /**
     * Configura um novo nível, reiniciando o tabuleiro, as tentativas do jogador
     * e o estado das variáveis de controlo de jogada.
     *
     * @param idNivel O identificador do nível (1 a 5).
     */
    public void mudarNivel(int idNivel){
        if (idNivel == 1) this.nivelAtual = new Nivel(1, 25, 4, 4);
        else if (idNivel == 2) this.nivelAtual = new Nivel(2, 20, 4, 4);
        else if (idNivel == 3) this.nivelAtual = new Nivel(3, 22, 4, 5);
        else if (idNivel == 4) this.nivelAtual = new Nivel(4, 18, 4, 5);
        else this.nivelAtual = new Nivel(5, 25, 6, 6);

        this.tabuleiro = new Tabuleiro(this.nivelAtual);
        this.jogador.setTentativas(this.nivelAtual.getMaxTentativas());
        this.paresEncontrados = 0;
        this.bloqueado = false;
        this.poderAtivado = "";
        this.modoRevelarEscolha = false;
    }

    /**
     * Processa a seleção de uma carta pelo utilizador.
     * Gere a lógica de comparação entre a primeira e a segunda carta,
     * consome tentativas e verifica a ativação de bónus.
     *
     * @param carta A carta selecionada pelo jogador.
     * @return true se a carta formou um par correto; false caso contrário.
     * @throws JogoException se o jogo estiver bloqueado ou a carta não puder ser selecionada.
     */
    public boolean escolherCarta(Carta carta) {
        if (bloqueado) throw new JogoException("Aguarde a resolução do turno corrente.");
        if(carta.getEstado() != EstadoCarta.VIRADA_BAIXO) throw new JogoException("Carta indisponível!");

        carta.virar();

        if (primeiraSelecionada == null) {
            primeiraSelecionada = carta;
            return false;
        }

        jogador.consumirTentativa();
        this.poderAtivado = "";

        if (primeiraSelecionada.getSimbolo().equals(carta.getSimbolo())){
            primeiraSelecionada.emparelhar();
            carta.emparelhar();
            paresEncontrados++;

            carta.ativar(this);
            if (this.poderAtivado != null && this.poderAtivado.toLowerCase().contains("tentativa")) {
                jogador.setTentativas(jogador.getTentativas() + 1);
            }

            primeiraSelecionada = null;
            return true;
        } else {
            bloqueado = true;
            return false;
        }
    }

    /**
     * Reseta o turno atual quando o jogador não forma um par,
     * virando as cartas novamente para baixo e desbloqueando a interação.
     *
     * @param segunda A segunda carta selecionada que não formou par.
     */
    public void limparTurnoIncorreto(Carta segunda){
        if (primeiraSelecionada != null){
            try {
                primeiraSelecionada.virar();
                segunda.virar();
            } catch (JogoException j){
                System.out.println("Deve apenas selecionar cartas viradas para baixo!");
            }
        }

        primeiraSelecionada = null;
        bloqueado = false;
    }

    /**
     * Verifica se o jogador completou o jogo ao encontrar todos os pares.
     * * @return true se o número de pares encontrados igualar a metade do total de cartas.
     */
    public boolean venceu() {
        return paresEncontrados == (tabuleiro.getCartas().size() / 2);
    }

    /**
     * Verifica se o jogador perdeu o jogo por esgotamento de tentativas.
     * * @return true se as tentativas chegaram a zero e o jogo não foi vencido.
     */
    public boolean perdeu() {
        return jogador.getTentativas() <= 0 && !venceu();
    }

    public void incrementarParesEncontrados(){
        this.paresEncontrados++;
    }

    public void avisarPoderAtivado(String poder){
        this.poderAtivado = poder;
    }

    public boolean isModoRevelarEscolha() { return modoRevelarEscolha; }

    public void setModoRevelarEscolha(boolean modo) { this.modoRevelarEscolha = modo; }
}
