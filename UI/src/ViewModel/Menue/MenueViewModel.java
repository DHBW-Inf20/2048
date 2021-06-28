package ViewModel.Menue;

import ViewModel.Game.GameViewModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenueViewModel extends Application implements IMenueViewModel, Initializable {

    //Binding Variablen
    @FXML
    private Slider sliderSize;
    @FXML
    private Button buttonNewGame;

    //Globale Variablen
    private int tileCount;

    //Einstelbare Konstanten
    int minWindowWidth = 500;
    int minWindowHeight = 700;
    int windowWidth = 600;
    int windowHeight = 800;

    /**
     * Wird beim öffnen der Applikation ausgeführt
     * Initalisiert die erste Szene (das Menü)
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Erzeuge eine Szene aus MenueView.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/View/MenueView.fxml"));
        primaryStage.setTitle("2048");

        //TODO: Resizeable ... Wie? ... Ist das so richtig?

        Scene menueScene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setScene(menueScene);

        primaryStage.setMinWidth(minWindowWidth);
        primaryStage.setMinHeight(minWindowHeight);
        //primaryStage.setResizable(false);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialisiert die interaktive Elemente (Slider)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initialisiere das Label
        tileCount = (int) sliderSize.getValue();
        buttonNewGame.setText("New " + tileCount + "x" + tileCount);

        //Anonymer ActionListener für den Slider -> stetzt das Label auf den Wert
        sliderSize.valueProperty().addListener((observable, oldValue, newValue) -> {

            tileCount = (int) sliderSize.getValue();
            buttonNewGame.setText("New " + tileCount + "x" + tileCount);
        });

    }

    /**
     * Wird beim Drücken von "New Game" ausgeführt
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressNewGame(ActionEvent event) throws IOException {

        //TODO: Ist es Klug alles an der gameBoard Size festzumachen?

        //Variablen mit einstellbaren Konstanten
        double gameBoardSize = 450;
        double gameBoardGap = gameBoardSize * 0.02;
        double tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;

        //Erzeuge eine Szene aus GameView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GameView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Übergebe dem Controler die notwendigen Daten
        GameViewModel gameViewModel = loader.getController();
        gameViewModel.setTileCount(tileCount);

        //Hole das Pane(/board) aus der .fxml anhand der ID
        Pane pane = (Pane) scene.lookup("#board");

        //Setze die größe des Panes auf die vorgegebenen
        pane.setPrefSize(gameBoardSize, gameBoardSize);
        pane.setMinHeight(gameBoardSize);
        pane.setMaxHeight(gameBoardSize);
        pane.setMinWidth(gameBoardSize);
        pane.setMaxWidth(gameBoardSize);

        //TODO: Restiliche Elemente Hinzufügen
        //Legt die größen der Elemente in Abhängigkeit der größe der Spielfelds fest
        HBox hBox = (HBox) scene.lookup("#gameBoardHBox");
        hBox.setPrefWidth(gameBoardSize);
        hBox.setMinWidth(gameBoardSize);

        VBox vBoxL = (VBox) scene.lookup("#gameBoardBVBoxL");
        vBoxL.setPrefWidth(gameBoardSize/2);
        vBoxL.setMinWidth(gameBoardSize/2);

        VBox vBoxR = (VBox) scene.lookup("#gameBoardBVBoxR");
        vBoxR.setPrefWidth(gameBoardSize/2);
        vBoxR.setMinWidth(gameBoardSize/2);

        //Erzeuge die Hintergurnd Tiles wie bei einem 2D-Array
        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                //Errechne die Position der einzelnen Tiles
                double posX = gameBoardGap * (j + 1) + (tileSize * j);
                double posY = gameBoardGap * (k + 1) + (tileSize * k);

                //Erzeuge Rechteck für die Tiles
                Rectangle rectangle = new Rectangle(posX, posY, tileSize, tileSize);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.setFill(Color.rgb(204, 192, 179));

                //rectangle.setStyle("-fx-background-color: \"#ccc0b3\"; -fx-background-radius: 50 50 50 50; -fx-border-radius: 50 50 50 50;");
                pane.getChildren().add(rectangle);
            }
        }

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Wird beim Drücken auf "Options" ausgeführt
     *
     * @param event
     */
    public void onButtonPressOptions(ActionEvent event) {

        //TODO: On buttonpress Option
    }

    /**
     * Wird beim Drücken auf "Credits" ausgeführt
     *
     * @param event
     */
    public void onButtonPressCredits(ActionEvent event) {

        //TODO: On buttonpress Credits
    }
}
