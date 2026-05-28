import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {
    private Carta primeiraCartaSelecionada = null;
    private Nivel nivel;
    private List<Carta> cartas;
    public Tabuleiro(Nivel nivelAtual) {
        this.nivel = nivel;
        this.cartas = new ArrayList<>();
    }

    public void inicializar() {
        cartas.clear();
        int numLinhas = nivel.getLinhas();
        int numColunas = nivel.getColunas();
        int totalCartas = numLinhas*numColunas;
        int totalPares= totalCartas/2;
        List <String> simbolos=new ArrayList<>(Arrays.asList("Gato","Cão","Passaro","Cobra","Peixe","Macaco","Girafa","Leão","Crocodilo","Tigre","Cabra","Vaca","Porco","Sapo","Tartaruga","Flamingo","Rato","Tubarão"));
        Collections.shuffle(simbolos);

        for(int i=0; i<totalPares-1;i++){
            cartas.add(new Carta(i*2,simbolos.get(i)));
            cartas.add(new Carta(i*2+1,simbolos.get(i)));
        }
        int proximoid = cartas.size();
        cartas.add(new Carta(proximoid,"Elefante"));
        cartas.add(new Carta(proximoid+1,"Elefante"));
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

    public boolean verificarPar(Carta c1, Carta c2) {
        if(c1.getSimbolo().equals(c2.getSimbolo())) return true;
        return false;
    }
    public int getTotalPares(){
        int cartasEmparelhadas = 0;
        for(Carta c : cartas){
            if(c.getEstado() == EstadoCarta.EMPARELHADA){
                cartasEmparelhadas++;
            }
        }
        return cartasEmparelhadas/2;

    }
    public void selecionarCarta(Carta cartaClicada){
        if(cartaClicada.getEstado() != EstadoCarta.VIRADA_BAIXO) return;
        cartaClicada.virar();
        if(primeiraCartaSelecionada == null) {
            primeiraCartaSelecionada = cartaClicada;
            return;
        }
        if(primeiraCartaSelecionada.getSimbolo().equals(cartaClicada.getSimbolo())){
            System.out.println("Par encontrado!");
            primeiraCartaSelecionada = null;
        }else{
            primeiraCartaSelecionada.virar();
            cartaClicada.virar();
            primeiraCartaSelecionada = null;
        }
    }
}
