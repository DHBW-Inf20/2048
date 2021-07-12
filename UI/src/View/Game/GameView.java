package View.Game;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView implements IGameView {

    //Binding Variablen
    @FXML
    private Label myLabel;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Label scoreLabel;

    //Globale Variablen
    private int minWindowWidth;
    private int minWindowHeight;
    private int windowWidth;
    private int windowHeight;

    private boolean kiMode;
    private int tileCount;

    private GameView gameView;

    /**
     * Kosntruktor
     */
    public GameView() {

    }

    /**
     * Erzeugt einen neue Scene für den Controler, wird vom Modusmenü aufgerufen
     *
     * @param event
     * @throws IOException
     */
    @Override
    public void createGameScene(Event event, Scene scene) throws IOException {

        //Variablen mit einstellbaren Konstanten
        double gameBoardSize = 450; // Bei verstellen müssen noch die Stadart fxml werte geändert werden -> Schriftgröße/png größen/etc
        double gameBoardGap = gameBoardSize * 0.02;
        double tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;
        double scoreBoxSize = ((gameBoardSize / 3) * 0.7);


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.PLUS) {
                System.out.println("+ key was pressed");

                int var = Integer.parseInt(scoreLabel.getText());
                setScoreLabel(var + 4);
            }
        });

        //Hole das Pane(/board) aus der .fxml anhand der ID
        Pane pane = (Pane) scene.lookup("#board");

        //Setze die größe des Panes auf die vorgegebenen
        pane.setPrefSize(gameBoardSize, gameBoardSize);
        pane.setMinHeight(gameBoardSize);
        pane.setMaxHeight(gameBoardSize);
        pane.setMinWidth(gameBoardSize);
        pane.setMaxWidth(gameBoardSize);

        //TODO: Restiliche Elemente Hinzufügen -> Menü/Back Button

        //Legt die größen der Elemente in Abhängigkeit der größe der Spielfelds fest
        HBox hBox = (HBox) scene.lookup("#gameBoardHBox");
        hBox.setPrefWidth(gameBoardSize);
        hBox.setMinWidth(gameBoardSize);

        //Unterteilt die Anzeigeelemente (obehalb gamboard) in 3 Abschnitte (jeweils 1/3 vom gamboard breit)
        VBox vBoxL = (VBox) scene.lookup("#gameBoardVBoxL");
        vBoxL.setPrefWidth(gameBoardSize / 3);
        vBoxL.setMinWidth(gameBoardSize / 3);

        VBox vBoxM = (VBox) scene.lookup("#gameBoardVBoxM");
        vBoxL.setPrefWidth(gameBoardSize / 3);
        vBoxL.setMinWidth(gameBoardSize / 3);

        VBox vBoxR = (VBox) scene.lookup("#gameBoardVBoxR");
        vBoxR.setPrefWidth(gameBoardSize / 3);
        vBoxR.setMinWidth(gameBoardSize / 3);

        Pane highscoreBox = (Pane) scene.lookup("#highscoreBox");
        highscoreBox.setPrefWidth(scoreBoxSize);
        highscoreBox.setPrefHeight(scoreBoxSize);
        highscoreBox.setMaxWidth(scoreBoxSize);
        highscoreBox.setMaxHeight(scoreBoxSize);

        Pane scoreBox = (Pane) scene.lookup("#scoreBox");
        scoreBox.setPrefWidth(scoreBoxSize);
        scoreBox.setPrefHeight(scoreBoxSize);
        scoreBox.setMaxWidth(scoreBoxSize);
        scoreBox.setMaxHeight(scoreBoxSize);


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

                pane.getChildren().add(rectangle);
            }
        }

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        //Hilfslabe zum debuggen
        setLabel();
    }


    /**
     * Setzt den KiMode, kommt vom Main menü
     *
     * @param kiMode
     */
    @Override
    public void setKiMode(boolean kiMode) {
        this.kiMode = kiMode;
    }

    /**
     * Setze die anzahl der Tiles, kommt vom Main menü
     *
     * @param tileCount
     */
    @Override
    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    /**
     * Setzt den Higscore, wird beim Fensteraufruf gesetzt
     *
     * @param highscore
     */
    @Override
    public void setHighscore(int highscore) {
        highscoreLabel.setText(Integer.toString(highscore));
    }


    /**
     * Setzt den Score, wird nach jedem Zug aufgerufen
     *
     * @param score
     */
    @Override
    public void setScoreLabel(int score) {
        scoreLabel.setText(Integer.toString(score));
    }

    /**
     * Bekommt die Fenster größe von der Vorgänger Methode
     *
     * @param windowWidth
     * @param windowHeight
     * @param minWindowWidth
     * @param minWindowHeight
     */
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = windowHeight;
    }


    public void setLabel() {
        if (kiMode) {
            myLabel.setText("KI -> ON!!!");
        } else {
            myLabel.setText("KI -> OFF!!!");
        }
    }
}
