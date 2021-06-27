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
import javafx.scene.layout.Pane;
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
    private int boardSize;


    /**
     * Wird beim öffnen der Applikation ausgeführt
     * Initalisiert die erste Szene (das Menü)
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/MenueView.fxml"));
        primaryStage.setTitle("2048");
        Scene menueScene = new Scene(root);
        primaryStage.setScene(menueScene);
        primaryStage.setResizable(false);


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
        boardSize = (int) sliderSize.getValue();
        buttonNewGame.setText("New " + boardSize + "x" + boardSize);

        //Anonymer ActionListener für den Slider -> stetzt das Label auf den Wert
        sliderSize.valueProperty().addListener((observable, oldValue, newValue) -> {

            boardSize = (int) sliderSize.getValue();
            buttonNewGame.setText("New " + boardSize + "x" + boardSize);
        });

    }


    /**
     * Wird beim Drücken von "New Game" ausgeführt
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressNewGame(ActionEvent event) throws IOException {

        //Erzeuge eine Szene aus GameView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/GameView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);


        //Übergebe dem Controler die notwendigen Daten
        GameViewModel gameViewModel = loader.getController();
        gameViewModel.setBoardSize(boardSize);

        Pane pane = (Pane) scene.lookup("#board");

        int gap = 7;

        for (int j = 0; j < boardSize; j++) {
            for (int k = 0; k < boardSize; k++) {

                //TODO: Kommentare

                //Errechne die Position der Tiles TODO: Außen nicht gap/2 sonder nur gap!
                double posX = ((300.0/boardSize) * j) + gap/2.0;
                double posY = ((300.0/boardSize) * k) + gap/2.0;

                //Erzeuge Rechteck;
                Rectangle rectangle = new Rectangle(posX, posY, 300.0 / boardSize - gap, 300.0 / boardSize - gap);
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
