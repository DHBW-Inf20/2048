package View.Game;


import Game.DataClasses.Directions;
import Game.DataClasses.Tile;
import View.Menue.MenueView;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;

public class GameView implements IGameView {

    //Binding Variablen
    @FXML
    private Label myLabel;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Label scoreLabel;

    //Globale Variablen

    //Fenstergrößen
    private int minWindowWidth;
    private int minWindowHeight;
    private int windowWidth;
    private int windowHeight;

    //Größen der Spielfeldelemente
    private double gameBoardSize;
    private double gameBoardGap;
    private double tileSize;
    private double scoreBoxSize;

    private boolean kiMode;
    private int tileCount;

    //Spielfelder die übergeben werden
    private Tile nextGameBoard[][];
    private Tile prevGameBoard[][];

    //Ist das "Spielfeld" worauf alles hinzugefügt und bewegt wird.
    private Pane pane;

    //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
    int variable = 0;
    Tile testBoard[][];

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
        gameBoardSize = 450; // Bei verstellen müssen noch die Stadart fxml werte geändert werden -> Schriftgröße/png größen/etc
        gameBoardGap = gameBoardSize * 0.02;
        tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;
        scoreBoxSize = ((gameBoardSize / 3) * 0.7);

        //Hole das Pane(/board) aus der .fxml anhand der ID
        pane = (Pane) scene.lookup("#board");

        //Setze die größe des Panes auf die vorgegebenen
        pane.setPrefSize(gameBoardSize, gameBoardSize);
        pane.setMinHeight(gameBoardSize);
        pane.setMaxHeight(gameBoardSize);
        pane.setMinWidth(gameBoardSize);
        pane.setMaxWidth(gameBoardSize);

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

        Button buttonMenue = (Button) scene.lookup("#buttonMenue");
        buttonMenue.setPrefWidth(scoreBoxSize);
        buttonMenue.setPrefHeight(scoreBoxSize / 4);
        buttonMenue.setMaxWidth(scoreBoxSize);
        buttonMenue.setMaxHeight(scoreBoxSize / 4);

        Button buttonHighscore = (Button) scene.lookup("#buttonHighscore");
        buttonHighscore.setPrefWidth(scoreBoxSize);
        buttonHighscore.setPrefHeight(scoreBoxSize / 4);
        buttonHighscore.setMaxWidth(scoreBoxSize);
        buttonHighscore.setMaxHeight(scoreBoxSize / 4);


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

        //TODO: HIER SPIELFELD ERZEUGEN ... WENN FERTIG
        //Get actual Gamboar

        prevGameBoard = new Tile[tileCount][tileCount];
        testBoard = new Tile[tileCount][tileCount];

        //Hilfslabe zum debuggen
        setLabel();

        //Keylistener auf der Scene
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                move(Directions.UP);
            }
            if (e.getCode() == KeyCode.S) {
                move(Directions.DOWN);
            }
            if (e.getCode() == KeyCode.A) {
                move(Directions.LEFT);
            }
            if (e.getCode() == KeyCode.D) {
                move(Directions.RIGHT);
            }
            //Score Test
            if (e.getCode() == KeyCode.PLUS) {
                System.out.println("+ key was pressed");

                int var = Integer.parseInt(scoreLabel.getText());
                setScoreLabel(var + 4);
            }
        });

    }


    /**
     * Wird beim Drücken des Buttons "Menu" ausgeführt -> Öffnet die Menü Scene
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressMenue(Event event) throws IOException {

        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menue/MenueView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        MenueView menueView = loader.getController();

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private void move(Directions direction) {

        boolean newTileFlag = true;

        //Hier gameBoard denn move(direction) übergeben und neues Array anfordern
        //Das hier ist erstmal eine Testmethode
        //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
        nextGameBoard = new Tile[tileCount][tileCount];
        nextGameBoard = getArrayForTesting();


        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                //Fall wenn keine Vorgänger da sind -> display new Pane
                if (nextGameBoard[j][k] != null && nextGameBoard[j][k].checkForPreTiles()) {

                    //Sucht im letzten Spielfeld nach dem Tile
                    for (int r = 0; r < tileCount; r++) {
                        for (int s = 0; s < tileCount; s++) {
                            if (nextGameBoard[j][k] == prevGameBoard[r][s]) {
                                //make transition
                                transition(nextGameBoard[j][k], j, k);

                                newTileFlag = false;
                                break;
                            }
                        }
                    }

                    //Wenn im letzten Spielfeld kein Tile gefunden wurde wird hier ein neues erzeugt
                    if (newTileFlag) {
                        System.out.println("neues Feld erzeugen");

                        Pane tilePane = nextGameBoard[j][k].getPane();

                        tilePane.setLayoutX(gameBoardGap * (j + 1) + (tileSize * j));
                        tilePane.setLayoutY(gameBoardGap * (k + 1) + (tileSize * k));

                        pane.getChildren().add(tilePane);
                    } else {
                        newTileFlag = true;
                    }
                }
            }
        }

        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                prevGameBoard[j][k] = nextGameBoard[j][k];
            }
        }
    }

    /**
     * Führt die Animation der Tiles aus
     *
     * @param tile
     * @param j    Zielposition X (jedoch nicht pixel Koordinaten sonder 1 -> 4/tilecount)
     * @param k    Zielposition Y (jedoch nicht pixel Koordinaten sonder 1 -> 4/tilecount)
     */
    private void transition(Tile tile, int j, int k) {

        TranslateTransition translateTransition = new TranslateTransition();

        Pane tilePane = tile.getPane();

        translateTransition.setToX((gameBoardGap * (j + 1) + (tileSize * j)) - tilePane.getLayoutX());
        translateTransition.setToY((gameBoardGap * (k + 1) + (tileSize * k)) - tilePane.getLayoutY());
        translateTransition.setNode(tilePane);
        translateTransition.setDuration(new Duration(100));
        translateTransition.play();
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

    //KANN später gelöscht werden nur zum Testen (oder in das Test Projekt verschben werden)
    //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
    //Erzeugt manuel ein Spielfeld Array
    public Tile[][] getArrayForTesting() {

        if (variable == 0) {

            System.out.println("Get First Array");
            testBoard[0][0] = new Tile(1, null, null, 0, 0);
            testBoard[0][3] = new Tile(2, null, null, 0, 3);
        }
        if (variable == 1) {
            System.out.println("Get Second Array");
            testBoard[3][0] = testBoard[0][0];
            testBoard[3][3] = testBoard[0][3];

            testBoard[3][0].setPosition(3, 0);
            testBoard[3][3].setPosition(3, 3);

            testBoard[0][0] = null;
            testBoard[0][3] = null;

            testBoard[1][2] = new Tile(5, null, null, 1, 2);
        }

        variable++;
        return testBoard;
    }
}
