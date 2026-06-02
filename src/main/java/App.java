import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import static javafx.application.Application.launch;

public class App extends Application {
    private Jogo jogo;
    private Label labelPontos;
    private Label labelTentativas;
    private Scene cenaEscolhaNiveis;
    private Button btnPrimeiraCarta;
    private VBox vBoxFundo;
    private VBox overlayFimDeJogo;
    private Label labelMensagemFoco;

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
        Button btnVoltarInicial = new Button("Voltar ao Início");

        HBox hbox1 = new HBox(20);
        VBox vBox2 = new VBox(20);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(button4,button5,button6,button7,button8);

        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label2,hbox1,btnVoltarInicial);
        cenaEscolhaNiveis = new Scene(vBox2, 500, 300);

        button4.setOnAction(e -> iniciarNivel(1, primaryStage));
        button5.setOnAction(e -> iniciarNivel(2, primaryStage));
        button6.setOnAction(e -> iniciarNivel(3, primaryStage));
        button7.setOnAction(e -> iniciarNivel(4, primaryStage));
        button8.setOnAction(e -> iniciarNivel(5, primaryStage));

        btnVoltarInicial.setOnAction(e -> {
            primaryStage.setScene(cena1);
            primaryStage.setTitle("Tela Inicial");
        });

        //-------- Navegação dos primeiros botoes (pagina inicial) ----------
        button1.setOnAction(event -> {
            jogo.mudarNivel(1);
            jogo.getTabuleiro().inicializar();
            jogo.getTabuleiro().embaralhar();
            primaryStage.setScene(criarCenaDoJogo(primaryStage));
        });

        button2.setOnAction(event -> {
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Niveis");
        });

        button3.setOnAction(event -> Platform.exit());
    }
    private void iniciarNivel(int idNivel, Stage primaryStage) {
        jogo.mudarNivel(idNivel);
        jogo.getTabuleiro().inicializar();
        jogo.getTabuleiro().embaralhar();
        btnPrimeiraCarta = null;
        Scene cenaDoJogo = criarCenaDoJogo(primaryStage);
        primaryStage.setScene(cenaDoJogo);
        primaryStage.setTitle("Nível " + idNivel);
    }
    private Scene criarCenaDoJogo(Stage primaryStage){
        vBoxFundo = new VBox(10);
        vBoxFundo.setAlignment(Pos.CENTER);

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

            btnCarta.setOnAction(event -> processarClique(cartaLogica, btnCarta, grid,primaryStage));

            grid.add(btnCarta,i%colunas,i/colunas);
        }

        Button btnDesistir = new Button("Desistir / Voltar aos Níveis");
        btnDesistir.setOnAction(e -> {
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Níveis");
        });

        vBoxFundo.getChildren().addAll(labelTitulo,infoBox,grid,btnDesistir);
        labelMensagemFoco = new Label();
        Button btnVoltarOverlay = new Button("Voltar aos Níveis");
        btnVoltarOverlay.setOnAction(e -> {
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Níveis");
        });
            overlayFimDeJogo = new VBox(20);
            overlayFimDeJogo.setAlignment(Pos.CENTER);
            overlayFimDeJogo.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75);");
            overlayFimDeJogo.getChildren().addAll(labelMensagemFoco, btnVoltarOverlay);
            overlayFimDeJogo.setVisible(false); // Começa invisível!

            StackPane root = new StackPane();
            root.getChildren().addAll(vBoxFundo, overlayFimDeJogo);
        return new Scene(root,400,500);
    }
    private void processarClique(Carta carta, Button btn, GridPane grid,Stage primaryStage){
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
                    labelMensagemFoco.setText("Vitória!\nParabéns!");
                    labelMensagemFoco.setStyle("-fx-text-fill: #4CAF50; -fx-font-size: 34px; -fx-font-weight: bold; -fx-text-alignment: center;");
                    vBoxFundo.setEffect(new GaussianBlur(12));
                    overlayFimDeJogo.setVisible(true);
                }
            }
            if(jogo.perdeu()){
                labelMensagemFoco.setText("Game Over!\nSem tentativas.");
                labelMensagemFoco.setStyle("-fx-text-fill: #F44336; -fx-font-size: 34px; -fx-font-weight: bold; -fx-text-alignment: center;");
                vBoxFundo.setEffect(new GaussianBlur(12));
                overlayFimDeJogo.setVisible(true);
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
    private void mostrarMensagemFimDeJogo(String titulo, String mensagem, Stage primaryStage) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        alerta.showAndWait();

        primaryStage.setScene(cenaEscolhaNiveis);
        primaryStage.setTitle("Menu dos Níveis");
    }
    private void atualizarLabels() {
        labelPontos.setText("Pontos: " + jogo.getJogador().getPontuacao());
        labelTentativas.setText("Tentativas: " + jogo.getJogador().getTentativas());
    }
    public static void main(String[] args) {
        launch(args);
    }
}