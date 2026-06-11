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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.List;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;

import javax.lang.model.element.NestingKind;

import static javafx.application.Application.launch;

public class App extends Application {
    private Jogo jogo;
    private List<Carta> cartasDouradas = new java.util.ArrayList<>();
    private boolean bloqueioPoder = false;
    private Label labelPontos;
    private Label labelTentativas;
    private Scene cenaEscolhaNiveis;
    private Scene cenaPrincipal;
    private Button btnPrimeiraCarta;
    private VBox vBoxFundo;
    private VBox overlayFimDeJogo;
    private Label labelMensagemFoco;
    private Label lblOverlayTitulo;
    private Label lblOverlayIcone;
    private Label lblOverlayPontos;
    private Label lblOverlayTentativas;
    private AudioClip somCarta = new AudioClip(new File("src/main/resources/sounds/cardsound.wav").toURI().toString());
    private AudioClip somBotao = new AudioClip(new File("src/main/resources/sounds/btnsound.wav").toURI().toString());
    private AudioClip somParCorreto = new AudioClip(new File("src/main/resources/sounds/correctcardsound.wav").toURI().toString());
    private AudioClip jogoPerdido = new AudioClip(new File("src/main/resources/sounds/failsound.wav").toURI().toString());
    private AudioClip jogoGanho = new AudioClip(new File("src/main/resources/sounds/winsound.wav").toURI().toString());
    private Media media = new Media(new File("src/main/resources/sounds/backgroundmusic.wav").toURI().toString());
    private MediaPlayer player = new MediaPlayer(media);

    public void start(Stage primaryStage){
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(2);
        player.play();
        Jogador jogador = new Jogador("Player 1",0,0);
        jogo = new Jogo(jogador);

        //----------------Tela Inicial----------------------------
        cenaPrincipal = criarCenaMenuPrincipal(primaryStage);
        cenaEscolhaNiveis = criarCenaEscolaNiveis(primaryStage);

        primaryStage.setTitle("Tela Inicial");
        primaryStage.setScene(cenaPrincipal);
        primaryStage.show();
        }

        private Scene criarCenaMenuPrincipal(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");
        //---------Titulo do jogo--------------
        VBox conteudoPrincipal = new VBox(50);
        conteudoPrincipal.setAlignment(Pos.CENTER);
        VBox blocoTitulo = new VBox(-15);
        blocoTitulo.setAlignment(Pos.CENTER);
        Label txtMemoria = new Label("Memória");
        Label txtDe = new Label("de");
        Label txtElefante = new Label("elefante");
        //--------Cores do titulo------------
        String estiloTitulo = "-fx-font-family: 'Arial Black'; -fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(to bottom, #fff779, #ff79b4); -fx-effect: dropshadow(one-pass-box, black, 0, 0, 4, 4);";
        txtMemoria.setStyle(estiloTitulo);
        txtDe.setStyle(estiloTitulo);
        txtElefante.setStyle(estiloTitulo);
        blocoTitulo.getChildren().addAll(txtMemoria,txtDe,txtElefante);
        //-------botoes---------
        VBox blocoBotoes = new VBox(20);
        blocoBotoes.setAlignment(Pos.CENTER);
        Button btnNovoJogo = new Button("▶  NOVO JOGO");
        Button btnContinuar = new Button("CONTINUAR");
        Button btnSair = new Button("✖  SAIR");
        //-------apresentacao dos botoes---------
        String estiloBotao = "-fx-background-color: #2D1A68; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px; -fx-border-color: white; -fx-border-width: 3px; -fx-background-radius: 20; -fx-border-radius: 18; -fx-min-width: 240px; -fx-min-height: 50px; -fx-cursor: hand;";
        btnNovoJogo.setStyle(estiloBotao);
        btnContinuar.setStyle(estiloBotao);
        btnSair.setStyle(estiloBotao);
        //----- Acoes dos botoes-----
            btnNovoJogo.setOnAction(event -> {
                somBotao.play();
                iniciarNivel(1,primaryStage);
            });
            btnContinuar.setOnAction(event -> {
                somBotao.play();
                primaryStage.setScene(cenaEscolhaNiveis);
                primaryStage.setTitle("Menu dos Niveis");
            });
            btnSair.setOnAction(event -> {
                somBotao.play();
                Platform.exit();
            });
            blocoBotoes.getChildren().addAll(btnNovoJogo,btnContinuar,btnSair);
            conteudoPrincipal.getChildren().addAll(blocoTitulo,blocoBotoes);
            root.getChildren().add(conteudoPrincipal);

            return new Scene(root,400,650);


    }
    private Scene criarCenaEscolaNiveis(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");

        VBox vBoxPrincipal = new VBox(30);
        vBoxPrincipal.setAlignment(Pos.CENTER);
        //----Titulo------
        Label lblTitulo = new Label("ESCOLHA O NÍVEL");
        lblTitulo.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 0, 0, 3, 3);");
        //-----botoes dos niveis------
        VBox caixaBotoes = new VBox(15);
        caixaBotoes.setAlignment(Pos.CENTER);
        String estiloBotao = "-fx-background-color: #2D1A68; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 18px; -fx-border-color: white; -fx-border-width: 3px; -fx-background-radius: 20; -fx-border-radius: 18; -fx-min-width: 220px; -fx-min-height: 50px; -fx-cursor: hand;";
        Button btnNivel1 = new Button("NÍVEL 1");
        Button btnNivel2 = new Button("NÍVEL 2");
        Button btnNivel3 = new Button("NÍVEL 3");
        Button btnNivel4 = new Button("NÍVEL 4");
        Button btnNivel5 = new Button("NÍVEL 5");

        btnNivel1.setStyle(estiloBotao);
        btnNivel2.setStyle(estiloBotao);
        btnNivel3.setStyle(estiloBotao);
        btnNivel4.setStyle(estiloBotao);
        btnNivel5.setStyle(estiloBotao);
        //-----Acoes dos botoes-----
        btnNivel1.setOnAction(event -> {
            somBotao.play();
            iniciarNivel(1,primaryStage);
        });
        btnNivel2.setOnAction(event -> {
            somBotao.play();
            iniciarNivel(2,primaryStage);
        });
        btnNivel3.setOnAction(event -> {
            somBotao.play();
            iniciarNivel(3,primaryStage);
        });
        btnNivel4.setOnAction(event -> {
            somBotao.play();
            iniciarNivel(4,primaryStage);
        });
        btnNivel5.setOnAction(event -> {
            somBotao.play();
            iniciarNivel(5,primaryStage);
        });
        caixaBotoes.getChildren().addAll(btnNivel1,btnNivel2,btnNivel3,btnNivel4,btnNivel5);
        //----botao voltar-------
        Button btnVoltar = new Button("«  VOLTAR AO INÍCIO");
        btnVoltar.setStyle("-fx-background-color: #2D1A68; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 16px; -fx-border-color: white; -fx-border-width: 3px; -fx-background-radius: 20; -fx-border-radius: 18; -fx-min-width: 220px; -fx-min-height: 45px; -fx-cursor: hand;");
        btnVoltar.setOnAction(event -> {
            somBotao.play();
            primaryStage.setScene(cenaPrincipal);
            primaryStage.setTitle("Tela Inicial");
        });
        vBoxPrincipal.getChildren().addAll(lblTitulo,caixaBotoes,btnVoltar);
        root.getChildren().add(vBoxPrincipal);

        return new Scene(root,400,650);

    }
    private void iniciarNivel(int idNivel, Stage primaryStage) {
        jogo.mudarNivel(idNivel);
        jogo.getTabuleiro().inicializar();
        jogo.getTabuleiro().embaralhar();
        jogo.getJogador().resetPontos();
        btnPrimeiraCarta = null;
        cartasDouradas.clear();
        Scene cenaDoJogo = criarCenaDoJogo(primaryStage);
        primaryStage.setScene(cenaDoJogo);
        primaryStage.setTitle("Nível " + idNivel);
    }
    private Scene criarCenaDoJogo(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");
        vBoxFundo = new VBox(25);
        vBoxFundo.setAlignment(Pos.CENTER);
       //-------Titulo do Nivel-------
        Label labelTitulo = new Label("NÍVEL "+jogo.getNivelAtual().getNumero());
        labelTitulo.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 38px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-transform: uppercase;");
        //------- Titulo dos as outras informacoes----------
        labelTentativas = new Label(("TENTATIVAS: " + jogo.getJogador().getTentativas()));
        labelPontos = new Label("PONTOS: " + jogo.getJogador().getPontuacao());

        String estiloInfo = "-fx-font-family: 'Arial Black'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;";
        labelTentativas.setStyle(estiloInfo);
        labelPontos.setStyle(estiloInfo);

        HBox infoBox = new HBox(40);
        infoBox.setAlignment(Pos.CENTER);
        infoBox.getChildren().addAll(labelTentativas,labelPontos);

        //-------Tabuleiro-------------
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(6);
        grid.setVgap(6);

        List<Carta> cartas = jogo.getTabuleiro().getCartas();
        int colunas = jogo.getNivelAtual().getColunas();

        double larguraCarta =(360.0/colunas);
        double alturaCarta = 60.0;

        int tamanhoLetra;
        if (colunas >= 5) {
            tamanhoLetra = 9;
        } else if (colunas == 4) {
            tamanhoLetra = 11;
        } else {
            tamanhoLetra = 13;
        }

        for (int i = 0; i<cartas.size(); i++){
            Carta cartaLogica = cartas.get(i);

            Button btnCarta= new Button("?");
            btnCarta.setPrefSize(larguraCarta, alturaCarta);
            btnCarta.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: " + tamanhoLetra + "px; -fx-padding: 0; -fx-cursor: hand; -fx-background-radius: 10; -fx-border-radius: 10;");

            btnCarta.setOnAction(event -> processarClique(cartaLogica, btnCarta, grid,primaryStage));

            grid.add(btnCarta,i%colunas,i/colunas);
        }

        Button btnDesistir = new Button("Desistir / Voltar aos Níveis");
        btnDesistir.setStyle("-fx-background-color: #2D1A68; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 16px; -fx-border-color: white; -fx-border-width: 3px; -fx-background-radius: 20; -fx-border-radius: 18; -fx-min-width: 180px; -fx-min-height: 45px; -fx-cursor: hand;");
        btnDesistir.setOnAction(e -> {
            somBotao.play();
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Níveis");
        });

        labelMensagemFoco = new Label();
        labelMensagemFoco.setVisible(false);
        labelMensagemFoco.managedProperty().bind(labelMensagemFoco.visibleProperty());
        labelMensagemFoco.setAlignment(Pos.CENTER);
        labelMensagemFoco.setWrapText(true);
        labelMensagemFoco.setMaxWidth(350);
        labelMensagemFoco.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        vBoxFundo.getChildren().addAll(labelTitulo,infoBox,labelMensagemFoco,grid,btnDesistir);
        overlayFimDeJogo = new VBox(25);
        overlayFimDeJogo.setAlignment(Pos.CENTER);
        overlayFimDeJogo.setStyle("-fx-background-color: rgba(20, 30, 55, 0.85);");
        overlayFimDeJogo.setVisible(false);
        lblOverlayTitulo = new Label();
        lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 32px; -fx-text-fill: white; -fx-text-alignment: center; -fx-font-weight: bold;");
        lblOverlayIcone = new Label();
        lblOverlayIcone.setStyle("-fx-font-size: 60px;");
        lblOverlayPontos = new Label();
        lblOverlayPontos.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        lblOverlayTentativas = new Label();
        lblOverlayTentativas.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        String estiloBotaoOverlay = "-fx-background-color: #2D1A68; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 16px; -fx-border-color: white; -fx-border-width: 3px; -fx-background-radius: 20; -fx-border-radius: 18; -fx-min-width: 220px; -fx-min-height: 48px; -fx-cursor: hand;";

        Button btnRepetir = new Button("↻  REPETIR");
        btnRepetir.setStyle(estiloBotaoOverlay);
        btnRepetir.setOnAction(e -> {
            somBotao.play();
            jogoGanho.stop();
            jogoPerdido.stop();
            iniciarNivel(jogo.getNivelAtual().getNumero(), primaryStage);
        });
        Button btnMenu = new Button("«  MENU");
        btnMenu.setStyle(estiloBotaoOverlay);
        btnMenu.setOnAction(e -> {
            somBotao.play();
            jogoGanho.stop();
            jogoPerdido.stop();
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Níveis");
        });
        overlayFimDeJogo.getChildren().addAll(
                lblOverlayTitulo,
                lblOverlayIcone,
                lblOverlayPontos,
                lblOverlayTentativas,
                btnRepetir,
                btnMenu
        );
        root.getChildren().addAll(vBoxFundo, overlayFimDeJogo);
        return new Scene(root,400,650);
    }
    private void processarClique(Carta carta, Button btn, GridPane grid,Stage primaryStage) {
        labelMensagemFoco.setVisible(false);
        if (!cartasDouradas.isEmpty()) {
            cartasDouradas.clear();
            atualizarTabuleiroSincronizado(grid);
        }
        if (bloqueioPoder || jogo.isBloqueado() || carta.getEstado() != EstadoCarta.VIRADA_BAIXO) return;
        somCarta.play();
        if (jogo.isModoRevelarEscolha()) {
            jogo.setModoRevelarEscolha(false);
            bloqueioPoder = true;

            carta.virar();
            atualizarTabuleiroSincronizado(grid);
            PauseTransition pausaEspreitar = new PauseTransition(Duration.seconds(2));
            pausaEspreitar.setOnFinished(e -> {
                carta.virar();
                atualizarTabuleiroSincronizado(grid);
                bloqueioPoder = false;
            });
            pausaEspreitar.play();
            return;
        }

        try {
            Carta primeira = jogo.getPrimeiraSelecionada();
            List<Carta> jaEstavamViradas = new java.util.ArrayList<>();
            for (Carta c : jogo.getTabuleiro().getCartas()) {
                if (c.getEstado() != EstadoCarta.VIRADA_BAIXO) {
                    jaEstavamViradas.add(c);
                }
            }
            boolean acertou = jogo.escolherCarta(carta);
            btn.setText(carta.getSimbolo());
            if (carta.getSimbolo().toLowerCase().contains("elefante")) {
                btn.setStyle(btn.getStyle() + " -fx-text-fill: #F44336;");
            } else {
                btn.setStyle(btn.getStyle() + " -fx-text-fill: #000000;");
            }

            atualizarLabels();
            if (!acertou && primeira != null) {
                PausarEEsconder(carta, primeira, btn, grid);
            } else if (acertou) {
                somParCorreto.play();

                String nomePoder = jogo.getPoderAtivado();
                if(nomePoder !=null && !nomePoder.trim().isEmpty()){
                    if(nomePoder.toLowerCase().contains("revelar")){
                        for (Carta c : jogo.getTabuleiro().getCartas()) {
                            if (c.getEstado() != EstadoCarta.VIRADA_BAIXO && !jaEstavamViradas.contains(c) && c != carta) {
                                if (!cartasDouradas.contains(c)) {
                                    cartasDouradas.add(c);
                                }
                            }
                        }

                    }
                    String textoParaMostrar = "✨ PODER: " + nomePoder.toUpperCase() + "✨";
                    labelMensagemFoco.setText(textoParaMostrar);
                    labelMensagemFoco.setStyle("-fx-background-color: rgba(45, 26, 104, 0.9); -fx-text-fill: #FFD700; -fx-font-size: 18px; -fx-font-family: 'Arial Black'; -fx-font-weight: bold; -fx-padding: 8 15 8 15; -fx-background-radius: 10; -fx-effect: dropshadow(one-pass-box, black, 0, 0, 3, 3);");
                    labelMensagemFoco.setVisible(true);
                }
                atualizarTabuleiroSincronizado(grid);
                atualizarLabels();

                if (jogo.venceu()){
                    player.pause();
                    jogoGanho.play();
                    PauseTransition pausaMusica1 = new PauseTransition(Duration.seconds(8));
                    pausaMusica1.setOnFinished(event -> player.play());
                    pausaMusica1.play();

                    lblOverlayTitulo.setText("NÍVEL " + jogo.getNivelAtual().getNumero() + "\nCONCLUÍDO!");
                    lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 32px; -fx-text-fill: #4CAF50; -fx-text-alignment: center; -fx-font-weight: bold;");


                    lblOverlayPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
                    lblOverlayTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());

                    vBoxFundo.setEffect(new GaussianBlur(12));
                    overlayFimDeJogo.setVisible(true);
                }
            }
            if (jogo.perdeu()) {
                player.pause();
                jogoPerdido.play();
                PauseTransition pausaMusica2 = new PauseTransition(Duration.seconds(2));
                pausaMusica2.setOnFinished(event -> player.play());
                pausaMusica2.play();

                lblOverlayTitulo.setText("NÍVEL " + jogo.getNivelAtual().getNumero() + "\nFALHADO!");
                lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 32px; -fx-text-fill: #F44336; -fx-text-alignment: center; -fx-font-weight: bold;");
                lblOverlayPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
                lblOverlayTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());

                vBoxFundo.setEffect(new GaussianBlur(12));
                overlayFimDeJogo.setVisible(true);
            }
        } catch (JogoException ex) {
            System.out.println(ex.getMessage());
        }
    }
        private void ativarPoderVisual(GridPane grid){
            PauseTransition pausaPoder = new PauseTransition(Duration.seconds(2));
            pausaPoder.setOnFinished(e -> {
                        labelMensagemFoco.setVisible(false);
                    });
            pausaPoder.play();
        }
    private void atualizarTabuleiroSincronizado(GridPane grid) {
        List<Carta> cartas = jogo.getTabuleiro().getCartas();
        int colunas = jogo.getNivelAtual().getColunas();

        int tamanhoLetra = (colunas >= 5) ? 9 : ((colunas == 4) ? 11 : 13);
        String estiloBase = "-fx-font-family: 'Arial Black'; -fx-font-size: " + tamanhoLetra + "px; -fx-padding: 0; -fx-cursor: hand; -fx-background-radius: 10; -fx-border-radius: 10;";

        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (grid.getChildren().get(i) instanceof Button) {
                Button btn = (Button) grid.getChildren().get(i);
                Carta c = cartas.get(i);

                if (c.getEstado() != EstadoCarta.VIRADA_BAIXO) {
                    btn.setText(c.getSimbolo());

                    if(cartasDouradas.contains(c)){
                        String estiloBordaDourada  = " -fx-border-color: #FFD700; -fx-border-width: 3px; -fx-border-radius: 8;";
                        if (c.getSimbolo().toLowerCase().contains("elefante")) {
                        btn.setStyle(estiloBase + estiloBordaDourada + " -fx-text-fill: #F44336;");
                    } else {
                        btn.setStyle(estiloBase + estiloBordaDourada + " -fx-text-fill: #000000;");
                    }
                } else {
                    if (c.getSimbolo().toLowerCase().contains("elefante")) {
                        btn.setStyle(estiloBase + " -fx-text-fill: #F44336;");
                    } else {
                        btn.setStyle(estiloBase + " -fx-text-fill: #000000;");
                    }
                }
            } else {
                btn.setText("?");
                btn.setStyle(estiloBase + " -fx-border-color: transparent; -fx-text-fill: black;");
            }
        }
    }
}
    private void PausarEEsconder(Carta segundaCartaLogica, Carta primeiraCartaLogica, Button btnSegunda, GridPane grid){
        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(event -> {
            jogo.limparTurnoIncorreto(segundaCartaLogica);
            btnSegunda.setText("?");
            btnSegunda.setStyle(btnSegunda.getStyle() + " -fx-text-fill: black;");
            for (javafx.scene.Node node : grid.getChildren()) {
                if (node instanceof Button) {
                    Button b = (Button) node;
                    // Procura o botão que tem o símbolo da primeira carta
                    if (b.getText().equals(primeiraCartaLogica.getSimbolo()) &&
                            primeiraCartaLogica.getEstado() == EstadoCarta.VIRADA_BAIXO) {
                        b.setText("?");
                        b.setStyle(b.getStyle() + " -fx-text-fill: black;");
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
        labelPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
        labelTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());
    }
    public static void main(String[] args) {
        launch(args);
    }
}