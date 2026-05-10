public class Jogador{
    private String nome;
    private int pontuacao;
    private int tentativas;

    public Jogador(String nome, int pontuacao, int tentativas){
        if(nome==null) throw new IllegalArgumentException("Tem de colocar nome");
        if(pontuacao<0) throw new IllegalArgumentException("A pontuação não pode ser negativa");
        if(tentativas<0) throw new IllegalArgumentException("As tentativas não podem ser negativas");
        this.nome=nome;
        this.pontuacao=pontuacao;
        this.tentativas=tentativas;
    }

    public int getTentativas() {
        return tentativas;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void adicionarPontos(int pontos){
        pontuacao+=pontos;
    }
    public void retirarTentativas(){
        tentativas--;
    }
    public void reset(int maxtentativas){
        pontuacao=0;
        tentativas= maxtentativas;
    }
}
