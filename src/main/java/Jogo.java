public class Jogo {
    private Jogador jogador;
    private Tabuleiro tabuleiro;
    private Nivel nivelAtual;
    private Carta primeiraSelecionada;
    private int paresEncontrados;
    private boolean bloqueado;
    private String poderAtivado;

    public Jogo(Jogador jogador){
        this.jogador = jogador;
        mudarNivel(1);
    }

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
    }

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

            primeiraSelecionada = null;
            return true;
        } else {
            bloqueado = true;
            return false;
        }
    }

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

    public boolean venceu() {
        return paresEncontrados == (tabuleiro.getCartas().size() / 2);
    }

    public boolean perdeu() {
        return jogador.getTentativas() <= 0 && !venceu();
    }

    public void incrementarParesEncontrados(){
        this.paresEncontrados++;
    }

    public void avisarPoderAtivado(String poder){
        this.poderAtivado = poder;
    }
}
