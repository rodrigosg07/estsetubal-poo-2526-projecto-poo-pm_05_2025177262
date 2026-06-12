import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe principal da "interface" gráfica do jogo "Memória de Elefante".
 * Funciona como o controlador principal no ecossistema JavaFX, gerindo a navegação
 * entre ecrãs, a apresentação visual do tabuleiro e a interação do utilizador.
 */
public class App extends Application {
    private Jogo jogo;
    private final List<Carta> cartasDouradas = new java.util.ArrayList<>();
    private boolean bloqueioPoder = false;
    private Label labelPontos;
    private Label labelTentativas;
    private Scene cenaEscolhaNiveis;
    private Scene cenaPrincipal;
    private VBox vBoxFundo;
    private VBox overlayFimDeJogo;
    private Label labelMensagemFoco;
    private Label lblOverlayTitulo;
    private ImageView imgOverlayIcone;
    private Label lblOverlayPontos;
    private Label lblOverlayTentativas;
    private final AudioClip somCarta = new AudioClip(new File("src/main/resources/sounds/cardsound.wav").toURI().toString());
    private final AudioClip somBotao = new AudioClip(new File("src/main/resources/sounds/btnsound.wav").toURI().toString());
    private final AudioClip somParCorreto = new AudioClip(new File("src/main/resources/sounds/correctcardsound.wav").toURI().toString());
    private final AudioClip jogoPerdido = new AudioClip(new File("src/main/resources/sounds/failsound.wav").toURI().toString());
    private final AudioClip jogoGanho = new AudioClip(new File("src/main/resources/sounds/winsound.wav").toURI().toString());
    private final Media media = new Media(new File("src/main/resources/sounds/backgroundmusic.wav").toURI().toString());
    private final MediaPlayer player = new MediaPlayer(media);

    /**
     * Ponto de arranque da aplicação JavaFX.
     * Inicializa o motor de áudio, cria a instância inicial do jogador e apresenta o menu principal.
     *
     * @param primaryStage O Stage principal (janela) da aplicação.
     */
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


    /**
    * Constrói a Scene correspondente ao ecrã do menu principal.
    *
    * @param primaryStage O Stage principal, passado aos botões para permitir a navegação.
    * @return A Scene configurada com o logótipo e as opções de jogo.
    */
    private Scene criarCenaMenuPrincipal(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");

        //--------- Logo do jogo --------------
        ImageView logoView = new ImageView();
        try {
            Image imgLogo = new Image(new File("src/main/resources/images/Logo.png").toURI().toString());
            logoView.setImage(imgLogo);
            logoView.setFitWidth(150);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Erro ao carregar o Logo: " + e.getMessage());
        }

        //---------Titulo do jogo--------------
        VBox conteudoPrincipal = new VBox(35);
        conteudoPrincipal.setAlignment(Pos.CENTER);
        VBox blocoTitulo = new VBox(-15);
        blocoTitulo.setAlignment(Pos.CENTER);
        Label txtMemoria = new Label("Memória");
        Label txtDe = new Label("de");
        Label txtElefante = new Label("elefante");

        //--------Cores do titulo------------
        String estiloTitulo = "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 50px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: linear-gradient(to bottom, #fff779, #ff79b4); " +
                "-fx-effect: dropshadow(one-pass-box, black, 0, 0, 4, 4);";
        txtMemoria.setStyle(estiloTitulo);
        txtDe.setStyle(estiloTitulo);
        txtElefante.setStyle(estiloTitulo);
        blocoTitulo.getChildren().addAll(txtMemoria,txtDe,txtElefante);

        //-------Botões---------
        VBox blocoBotoes = new VBox(20);
        blocoBotoes.setAlignment(Pos.CENTER);
        Button btnNovoJogo = new Button("▶  NOVO JOGO");
        Button btnContinuar = new Button("CONTINUAR");
        Button btnSair = new Button("✖  SAIR");

        //-------Apresentacao dos botões---------
        String estiloBotao = "-fx-background-color: #2D1A68; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 18px; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 18; " +
                "-fx-min-width: 240px; " +
                "-fx-min-height: 50px; " +
                "-fx-cursor: hand;";
        btnNovoJogo.setStyle(estiloBotao);
        btnContinuar.setStyle(estiloBotao);
        btnSair.setStyle(estiloBotao);

        //----- Ações dos botões-----
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
        conteudoPrincipal.getChildren().addAll(logoView, blocoTitulo,blocoBotoes);
        root.getChildren().add(conteudoPrincipal);

        return new Scene(root,400,650);
    }

    /**
     * Constrói a Scene correspondente ao menu de seleção de níveis.
     *
     * @param primaryStage O Stage principal para redirecionamento das opções.
     * @return A Scene com os botões de todos os níveis disponíveis.
     */
    private Scene criarCenaEscolaNiveis(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");
        VBox vBoxPrincipal = new VBox(30);
        vBoxPrincipal.setAlignment(Pos.CENTER);

        //----Titulo------
        Label lblTitulo = new Label("ESCOLHA O NÍVEL");
        lblTitulo.setStyle("-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-effect: dropshadow(one-pass-box, black, 0, 0, 3, 3);");

        //-----Botões dos niveis------
        VBox caixaBotoes = new VBox(15);
        caixaBotoes.setAlignment(Pos.CENTER);
        String estiloBotao = "-fx-background-color: #2D1A68; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 18px; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 18; " +
                "-fx-min-width: 220px; " +
                "-fx-min-height: 50px; " +
                "-fx-cursor: hand;";
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

        //-----Ações dos botões-----
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

        //----Botao voltar-------
        Button btnVoltar = getButton(primaryStage, somBotao, cenaPrincipal);
        vBoxPrincipal.getChildren().addAll(lblTitulo,caixaBotoes,btnVoltar);
        root.getChildren().add(vBoxPrincipal);

        return new Scene(root,400,650);
    }

    private static Button getButton(Stage primaryStage, AudioClip somBotao, Scene cenaPrincipal) {
        Button btnVoltar = new Button("«  VOLTAR AO INÍCIO");
        btnVoltar.setStyle("-fx-background-color: #2D1A68; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 16px; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 18; " +
                "-fx-min-width: 220px; " +
                "-fx-min-height: 45px; -fx-cursor: hand;");
        btnVoltar.setOnAction(event -> {
            somBotao.play();
            primaryStage.setScene(cenaPrincipal);
            primaryStage.setTitle("Tela Inicial");
        });
        return btnVoltar;
    }


    /**
     * Prepara a lógica e a "interface" para iniciar um nível específico.
     * Atualiza o modelo de jogo, reinicia os pontos, baralha as cartas e carrega o novo ecrã.
     *
     * @param idNivel O identificador do nível a carregar (1 a 5).
     * @param primaryStage O Stage onde a nova Scene do jogo será injetada.
     */
    private void iniciarNivel(int idNivel, Stage primaryStage) {
        jogo.mudarNivel(idNivel);
        jogo.getTabuleiro().inicializar();
        jogo.getTabuleiro().embaralhar();
        jogo.getJogador().resetPontos();
        cartasDouradas.clear();
        Scene cenaDoJogo = criarCenaDoJogo(primaryStage);
        primaryStage.setScene(cenaDoJogo);
        primaryStage.setTitle("Nível " + idNivel);
    }


    /**
     * Constrói a Scene principal do modo de jogo, incluindo o painel de cartas dinâmico (GridPane)
     * adaptado às dimensões do nível atual e as métricas do jogador.
     *
     * @param primaryStage O Stage principal para navegação (ex: botão de desistir).
     * @return A Scene que contém a grelha de cartas e overlays de fim de jogo.
     */
    private Scene criarCenaDoJogo(Stage primaryStage){
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #4286f4;");
        vBoxFundo = new VBox(25);
        vBoxFundo.setAlignment(Pos.CENTER);

       //-------Titulo do Nivel-------
        Label labelTitulo = new Label("NÍVEL "+jogo.getNivelAtual().getNumero());
        labelTitulo.setStyle("-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 38px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-text-transform: uppercase;");

        //------- Titulo dos as outras informações----------
        labelTentativas = new Label(("TENTATIVAS: " + jogo.getJogador().getTentativas()));
        labelPontos = new Label("PONTOS: " + jogo.getJogador().getPontuacao());

        String estiloInfo = "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white;";
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
        int linhas = jogo.getNivelAtual().getLinhas();

        double larguraCarta =(360.0/colunas);
        double alturaCarta = (linhas > 4) ? (380.0 / linhas) : 100.0;

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
            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle();
            clip.widthProperty().bind(btnCarta.widthProperty());
            clip.heightProperty().bind(btnCarta.heightProperty());
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            btnCarta.setClip(clip);
            btnCarta.setStyle("-fx-font-family: 'Arial Black'; " +
                    "-fx-font-size: " + tamanhoLetra + "px; " +
                    "-fx-padding: 0; -fx-cursor: hand; " +
                    "-fx-background-radius: 15; " +
                    "-fx-border-radius: 15;");

            btnCarta.setOnAction(event -> processarClique(cartaLogica, grid,primaryStage));

            grid.add(btnCarta,i%colunas,i/colunas);
        }

        Button btnDesistir = getButton(primaryStage);
        VBox.setMargin(btnDesistir, new javafx.geometry.Insets(10, 0, 20, 0));

        labelMensagemFoco = new Label();
        labelMensagemFoco.setVisible(false);
        labelMensagemFoco.setPrefHeight(38);
        labelMensagemFoco.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        labelMensagemFoco.setMaxWidth(Double.MAX_VALUE);
        labelMensagemFoco.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        labelMensagemFoco.setAlignment(javafx.geometry.Pos.CENTER);

        VBox gridContainer = new VBox(12);
        gridContainer.setAlignment(Pos.CENTER);
        gridContainer.getChildren().addAll(labelMensagemFoco, grid);
        vBoxFundo.getChildren().addAll(labelTitulo, infoBox, gridContainer, btnDesistir);
        overlayFimDeJogo = new VBox(25);
        overlayFimDeJogo.setAlignment(Pos.CENTER);
        overlayFimDeJogo.setStyle("-fx-background-color: rgba(20, 30, 55, 0.85);");
        overlayFimDeJogo.setVisible(false);
        lblOverlayTitulo = new Label();
        lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 32px; -fx-text-fill: white; " +
                "-fx-text-alignment: center; " +
                "-fx-font-weight: bold;");
        imgOverlayIcone = new ImageView();
        imgOverlayIcone.setFitHeight(120);
        imgOverlayIcone.setPreserveRatio(true);
        lblOverlayPontos = new Label();
        lblOverlayPontos.setStyle("-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 24px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold;");

        lblOverlayTentativas = new Label();
        lblOverlayTentativas.setStyle("-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 24px; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold;");
        String estiloBotaoOverlay = "-fx-background-color: #2D1A68; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 16px; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 18; " +
                "-fx-min-width: 220px; " +
                "-fx-min-height: 48px; " +
                "-fx-cursor: hand;";

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
                imgOverlayIcone,
                lblOverlayPontos,
                lblOverlayTentativas,
                btnRepetir,
                btnMenu
        );
        root.getChildren().addAll(vBoxFundo, overlayFimDeJogo);
        return new Scene(root,400,650);
    }

    private Button getButton(Stage primaryStage) {
        Button btnDesistir = new Button("Desistir / Voltar aos Níveis");
        btnDesistir.setStyle("-fx-background-color: #2D1A68; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: 16px; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 3px; " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 18; " +
                "-fx-min-width: 180px; " +
                "-fx-min-height: 45px; " +
                "-fx-cursor: hand;");
        btnDesistir.setOnAction(e -> {
            somBotao.play();
            primaryStage.setScene(cenaEscolhaNiveis);
            primaryStage.setTitle("Menu dos Níveis");
        });
        return btnDesistir;
    }


    /**
     * Processa a lógica de interação quando o utilizador clica numa carta no tabuleiro.
     * Faz a gestão de poderes especiais (ex: Revelar Escolha), verifica os pares,
     * atualiza o estado visual das cartas e valida as condições de vitória ou derrota.
     *
     * @param carta A instância lógica da carta que foi clicada.
     * @param grid O GridPane que contém as cartas visuais, para ser atualizado.
     * @param primaryStage O Stage principal, usado para apresentar sobreposições (overlays) de fim de jogo.
     */
    private void processarClique(Carta carta, GridPane grid, Stage primaryStage) {
        labelMensagemFoco.setVisible(false);
        if (!cartasDouradas.isEmpty()) {
            cartasDouradas.clear();
            atualizarTabuleiroSincronizado(grid, primaryStage);
        }
        if (bloqueioPoder || jogo.isBloqueado() || carta.getEstado() != EstadoCarta.VIRADA_BAIXO) return;
        somCarta.play();
        if (jogo.isModoRevelarEscolha()) {
            jogo.setModoRevelarEscolha(false);
            bloqueioPoder = true;

            carta.virar();
            atualizarTabuleiroSincronizado(grid, primaryStage);
            PauseTransition pausaEspreitar = new PauseTransition(Duration.seconds(2));
            pausaEspreitar.setOnFinished(e -> {
                carta.virar();
                atualizarTabuleiroSincronizado(grid, primaryStage);
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
            atualizarTabuleiroSincronizado(grid, primaryStage);

            atualizarLabels();
            if (!acertou && primeira != null) {
                PausarEEsconder(carta, grid, primaryStage);
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
                    labelMensagemFoco.setStyle("-fx-background-color: rgba(45, 26, 104, 0.9); " +
                            "-fx-text-fill: #FFD700; " +
                            "-fx-font-size: 14px; " +
                            "-fx-font-family: 'Arial'; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 6 14 6 14; " +
                            "-fx-background-radius: 10; " +
                            "-fx-effect: dropshadow(one-pass-box, black, 0, 0, 3, 3);");
                    labelMensagemFoco.setVisible(true);
                }
                atualizarTabuleiroSincronizado(grid, primaryStage);
                atualizarLabels();

                if (jogo.venceu()){
                    player.pause();
                    jogoGanho.play();
                    PauseTransition pausaMusica1 = new PauseTransition(Duration.seconds(8));
                    pausaMusica1.setOnFinished(event -> player.play());
                    pausaMusica1.play();

                    lblOverlayTitulo.setText("NÍVEL " + jogo.getNivelAtual().getNumero() + "\nCONCLUÍDO!");
                    lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; " +
                            "-fx-font-size: 32px; " +
                            "-fx-text-fill: #4CAF50; " +
                            "-fx-text-alignment: center; " +
                            "-fx-font-weight: bold;");


                    lblOverlayPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
                    lblOverlayTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());

                    try {
                        Image imagemVitoria = new Image(new File("src/main/resources/images/Win.png").toURI().toString());
                        imgOverlayIcone.setImage(imagemVitoria);
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem de vitória.");
                    }

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
                lblOverlayTitulo.setStyle("-fx-font-family: 'Arial Black'; " +
                        "-fx-font-size: 32px; " +
                        "-fx-text-fill: #F44336; " +
                        "-fx-text-alignment: center; " +
                        "-fx-font-weight: bold;");
                lblOverlayPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
                lblOverlayTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());

                try {
                    Image imagemDerrota = new Image(new File("src/main/resources/images/lose.png").toURI().toString());
                    imgOverlayIcone.setImage(imagemDerrota);
                } catch (Exception e) {
                    System.out.println("Erro ao carregar imagem de derrota.");
                }

                vBoxFundo.setEffect(new GaussianBlur(12));
                overlayFimDeJogo.setVisible(true);
            }
        } catch (JogoException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Sincroniza o estado visual das cartas no JavaFX (botões) com o estado interno do modelo lógico.
     * Responsável por atualizar as imagens de fundo e aplicar efeitos visuais (como as bordas douradas).
     *
     * @param grid O GridPane contendo os botões que representam as cartas.
     * @param primaryStage O Stage principal.
     */
    private void atualizarTabuleiroSincronizado(GridPane grid, Stage primaryStage) {
        List<Carta> cartas = jogo.getTabuleiro().getCartas();
        int colunas = jogo.getNivelAtual().getColunas();

        int tamanhoLetra = (colunas >= 5) ? 9 : ((colunas == 4) ? 11 : 13);
        String estiloBase = "-fx-font-family: 'Arial Black'; " +
                "-fx-font-size: " + tamanhoLetra + "px; " +
                "-fx-cursor: hand; " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 15; " +
                "-fx-background-clip: padding-box;";

        for (int i = 0; i < grid.getChildren().size(); i++) {
            if (grid.getChildren().get(i) instanceof Button btn) {
                Carta c = cartas.get(i);

                btn.setOnAction(event -> processarClique(c, grid, primaryStage));

                if (c.getEstado() != EstadoCarta.VIRADA_BAIXO) {
                    btn.setText(null);
                    btn.setGraphic(null);

                    String estiloBordaDourada = cartasDouradas.contains(c) ? " -fx-border-color: #FFD700; " +
                                                                             "-fx-border-width: 3px;" : " " +
                                                                             "-fx-border-color: transparent;";

                    try {
                        String estiloImagem = getString(c);

                        btn.setStyle(estiloBase + estiloImagem + estiloBordaDourada);
                    } catch (Exception e) {
                        btn.setText(c.getSimbolo());
                        btn.setStyle(estiloBase + estiloBordaDourada + " -fx-text-fill: black; " +
                                "-fx-background-color: white;");
                    }
                } else {
                    btn.setText("?");
                    btn.setGraphic(null);
                    btn.setStyle(estiloBase + " -fx-background-color: #e0e0e0; " +
                            "-fx-border-color: transparent; " +
                            "-fx-text-fill: black;");
                }
            }
        }
    }

    private static String getString(Carta c) {
        String pathImagem = new File("src/main/resources/images/" + c.getSimbolo() + ".png").toURI().toString();
        return " -fx-background-image: url('" + pathImagem + "'); " +
                " -fx-background-size: 100% 100%; " +
                " -fx-background-position: center; " +
                " -fx-background-repeat: no-repeat; " +
                " -fx-background-color: white;" +
                " -fx-background-radius: 15; ";
    }

    private void PausarEEsconder(Carta segundaCartaLogica, GridPane grid, Stage primaryStage){
        PauseTransition pausa = new PauseTransition(Duration.seconds(1));
        pausa.setOnFinished(event -> {
            jogo.limparTurnoIncorreto(segundaCartaLogica);
            atualizarTabuleiroSincronizado(grid, primaryStage);
            atualizarLabels();
        });
        pausa.play();
    }

    private void atualizarLabels() {
        labelPontos.setText("PONTOS: " + jogo.getJogador().getPontuacao());
        labelTentativas.setText("TENTATIVAS: " + jogo.getJogador().getTentativas());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
