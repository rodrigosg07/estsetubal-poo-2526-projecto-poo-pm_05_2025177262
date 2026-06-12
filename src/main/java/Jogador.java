public class Jogador{
    private int pontuacao;
    private int tentativas;

    public Jogador(String nomeJogador, int pontuacao, int tentativas){
        if(nomeJogador==null) throw new JogadorException("Tem de colocar nome");
        if(pontuacao<0) throw new JogadorException("A pontuação não pode ser negativa");
        this.pontuacao=pontuacao;
        setTentativas(tentativas);
    }

    public int getTentativas() {
        return tentativas;
    }

    public void setTentativas(int tentativas) {
        if(tentativas<0) throw new JogadorException("As tentativas não podem ser negativas");
        this.tentativas = tentativas;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void incrementarTentativas(int t){
        this.tentativas += t;
    }
    public void adicionarPontos(int pontos){
        pontuacao+=pontos;
    }
    public void consumirTentativa(){
        tentativas--;
    }
    public void resetTentativas(){
        pontuacao=0;
    }
    public void resetPontos(){
        this.pontuacao=0;
    }
}
