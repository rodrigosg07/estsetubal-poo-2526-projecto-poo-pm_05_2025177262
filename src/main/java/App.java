import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLOutput;
import java.util.List;

import static javafx.application.Application.launch;

public class App extends Application {
    private Jogo jogo;
    private Label labelPontos;
    private Label labelTentativas;

    public void start(Stage primaryStage){
        Jogador jogador = new Jogador("Player 1",0,0);
        jogo = new Jogo(jogador);

        //----------------Tela Inicial (cena1)----------------------------

        Label label1 = new Label("Jogo da Memória");
        Button button1 = new Button("Novo Jogo");
        Button button2 = new Button("Continuar");
        Button button3 = new Button("Sair");
        VBox vBox1 = new VBox(20);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(label1, button1, button2, button3);
        Scene cena1 = new Scene(vBox1,400,300);

        primaryStage.setTitle("Tela Inicial");
        primaryStage.setScene(cena1);
        primaryStage.show();

        //--------tela de escolha de nivel (cena2)---------------------

        Label label2 = new Label("Escolha o nivel:");
        Button button4 = new Button("Nivel 1");
        Button button5 = new Button("Nivel 2");
        Button button6 = new Button("Nivel 3");
        Button button7 = new Button("Nivel 4");
        Button button8 = new Button("Nivel 5");
        HBox hbox1 = new HBox(20);
        VBox vBox2 = new VBox(20);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(button4,button5,button6,button7,button8);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label1,label2,hbox1);
        Scene cena2 = new Scene(vBox2,400,300);

        //-------------Nivel1 (cena3)--------------
        jogo.mudarNivel(1);
        jogo.getTabuleiro().inicializar();
        jogo.getTabuleiro().embaralhar();

        Scene cena3 = criarCenaDoJogo();
        primaryStage.setTitle("Nivel 1");

        //-------- Navegação entre cenas ----------
        button1.setOnAction(event -> {
            jogo.mudarNivel(1);
            jogo.getTabuleiro().inicializar();
            jogo.getTabuleiro().embaralhar();
            primaryStage.setScene(criarCenaDoJogo());
        });

        button2.setOnAction(event -> {
            primaryStage.setScene(cena2);
            primaryStage.setTitle("Menu dos Niveis");
        });

        button3.setOnAction(event -> Platform.exit());
    }
    private Scene criarCenaDoJogo(){
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        Label labelTitulo= new Label("Nivel: " + jogo.getNivelAtual().getNumero());
        labelPontos = new Label("Pontos: " + jogo.getJogador().getPontuacao());
        labelTentativas = new Label(("Tentativas: " + jogo.getJogador().getTentativas()));

        HBox infoBox = new HBox(20);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.getChildren().addAll(labelPontos,labelTentativas);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        List<Carta> cartas = jogo.getTabuleiro().getCartas();
        int colunas = jogo.getNivelAtual().getColunas();

        for (int i = 0; i<cartas.size(); i++){
            Carta cartaLogica = cartas.get(i);

            Button btnCarta= new Button("?");
            btnCarta.setPrefSize(80,80);

            btnCarta.setOnAction(event -> processarClique(cartaLogica, btnCarta, grid));

            grid.add(btnCarta,i%colunas,i/colunas);
        }
        vBox.getChildren().addAll(labelTitulo,labelPontos,infoBox,grid);
        return new Scene(vBox,400,500);
    }
    private void processarClique(Carta carta, Button btn, GridPane grid){
        if(jogo.isBloqueado() || carta.getEstado() != EstadoCarta.VIRADA_BAIXO) return;

        try{
            Carta primeira = jogo.getPrimeiraSelecionada();
            boolean acertou = jogo.escolherCarta(carta);
            btn.setText(carta.getSimbolo());
            atualizarLabels();
            if(!acertou && primeira != null){
                PausarEEsconder(carta, primeira, btn, grid);
            }else if(acertou){
                if(!jogo.getPoderAtivado().isEmpty()){
                    System.out.println("Poder: "+jogo.getPoderAtivado());
                }
                if(jogo.venceu()){
                    System.out.println("Ganhaste o nível!");
                }
            }
            if(jogo.perdeu()){
                System.out.println("Perdeste! Sem tentativas.");
            }
        } catch (JogoException ex){
            System.out.println(ex.getMessage());
        }
    }
    private void PausarEEsconder(Carta segundaCartaLogica, Carta primeiraCartaLogica, Button btnSegunda, GridPane grid){
        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(event -> {
            jogo.limparTurnoIncorreto(segundaCartaLogica);
            btnSegunda.setText("?");
            for (javafx.scene.Node node : grid.getChildren()) {
                if (node instanceof Button) {
                    Button b = (Button) node;
                    // Procura o botão que tem o símbolo da primeira carta
                    if (b.getText().equals(primeiraCartaLogica.getSimbolo()) &&
                            primeiraCartaLogica.getEstado() == EstadoCarta.VIRADA_BAIXO) {
                        b.setText("?");
                    }
                }
            }
            atualizarLabels();
        });
        pausa.play();
    }
    private void atualizarLabels() {
        labelPontos.setText("Pontos: " + jogo.getJogador().getPontuacao());
        labelTentativas.setText("Tentativas: " + jogo.getJogador().getTentativas());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
//nivel2 - 16, nivel3 - 20, nivel4 - 20, nivel5 - 36