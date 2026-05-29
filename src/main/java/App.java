import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage primaryStage){
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
        Label label2 = new Label("Escolha o nivel:");
        Button button4 = new Button("Nivel 1");
        Button button5 = new Button("Nivel 2");
        Button button6 = new Button("Nivel 3");
        Button button7 = new Button("Nivel 4");
        Button button8 = new Button("Nivel 5");
        VBox vBox2 = new VBox(20);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(label2,button4,button5,button6,button7,button8);
        Scene cena2 = new Scene(vBox2,400,300);
        //botao1...
        button2.setOnAction(event -> primaryStage.setScene(cena2));
        button3.setOnAction(event -> Platform.exit());


    }
    public static void main(String[] args) {
        launch(args);
    }
}
