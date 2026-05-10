public abstract class Carta {
    private int id;
    private String simbolo;
    private EstadoCarta estado;

    //protected para apenas ser acessada pela propria classe e seus "filhos"
    protected Carta(int id, String simbolo){
        if (simbolo == null || simbolo.isBlank()){
            throw new IllegalArgumentException("O símbolo não pode estar vazio");
        }
        this.id = id;
        this.simbolo = simbolo;
        this.estado = EstadoCarta.VIRADA_BAIXO;
    }

    //Coloca carta selecionada pelo jogador virada para cima(caso não esteja emparelhada)
    public void virar(){
        if (estado == EstadoCarta.VIRADA_BAIXO){
            estado = EstadoCarta.VIRADA_CIMA;
        }
    }

    //Marca a carta como parte de um par encontrado
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

    //Carta não emparelhada deve voltar a ser virada para baixo
    public void resetEstado(){
        estado = EstadoCarta.VIRADA_BAIXO;
    }













}
