package View.Game;


import Factory.ComponentFactory;
import Game.DataClasses.Directions;
import Game.DataClasses.GameModes;
import Game.DataClasses.Tile;
import Game.IGameController;
import HighScore.IHighScoreController;
import View.Menue.MenueView;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;



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
    private GameModes gameMode;

    //Spielfelder die übergeben werden
    private Tile[][] nextGameBoard;
    private Tile[][] prevGameBoard;

    private Scene scene;
    //Ist das "Spielfeld" worauf alles hinzugefügt und bewegt wird.
    private Pane pane;

    //Im Spiel -> 0 | Wenn Gewonnen -> 1 | Nach gewinn weiterspielen -> 2
    private int winScreen = 0;

    //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
    int variable = 0;
    Tile[][] testBoard;



    private final IGameController gameController;
    private final IHighScoreController highScoreController;


    /**
     * Kosntruktor
     */

    public GameView() {

        this.gameController = ComponentFactory.getGameController();
        this.highScoreController = ComponentFactory.getHighScroeController();

    }

    /**
     * Erzeugt einen neue Scene für den Controler, wird vom Modusmenü aufgerufen
     *
     * @param event
     * @throws IOException
     */
    @Override
    public void createGameScene(Event event, Scene scene) throws IOException {

        this.gameController.setTileChangeListener(tiles ->
        {
            nextGameBoard = tiles;
            System.out.println("Set new GameBoard in GameView!");
        });

        this.gameController.setScoreChangeListener(newScore -> {
            setScoreLabel(newScore);
            highScoreController.submitNewScore(newScore,tileCount);
            setHighscore(highScoreController.getCurrentHighScoreData(tileCount).getScore());
        });


        //Variablen mit einstellbaren Konstanten
        gameBoardSize = 450; // Bei verstellen müssen noch die Stadart fxml werte geändert werden -> Schriftgröße/png größen/etc
        gameBoardGap = gameBoardSize * 0.02;
        tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;
        scoreBoxSize = ((gameBoardSize / 3) * 0.7);

        //Hole das Pane(/board) aus der .fxml anhand der ID
        pane = (Pane) scene.lookup("#board");
        this.scene = scene;

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


        gameController.startGame(gameMode, true, tileCount);

        prevGameBoard = new Tile[tileCount][tileCount];

        //Initialisierungs Move
        move();

        //testBoard = new Tile[tileCount][tileCount];

        //Hilfslabe zum debuggen
        setLabel();


        //Keylistener auf der Scene
        scene.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.W) {
                gameController.makeMove(Directions.UP);
                move();
            }
            if (e.getCode() == KeyCode.S) {
                gameController.makeMove(Directions.DOWN);
                move();
            }
            if (e.getCode() == KeyCode.A) {
                gameController.makeMove(Directions.LEFT);
                move();
            }
            if (e.getCode() == KeyCode.D) {
                gameController.makeMove(Directions.RIGHT);
                move();
            }
            //Score Test
            if (e.getCode() == KeyCode.PLUS) {
                System.out.println("+ key was pressed");

                int var = Integer.parseInt(scoreLabel.getText());
                if (var == 0) {
                    var = 1;
                }
                setScoreLabel(2 * var);
            }
        });

        setHighscore(highScoreController.getCurrentHighScoreData(tileCount).getScore());
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


    private void move() {


        boolean newTileFlag = true;

        //Hier gameBoard denn move(direction) übergeben und neues Array anfordern
        //Das hier ist erstmal eine Testmethode
        //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
        //nextGameBoard = new Tile[tileCount][tileCount];

        /*
        if(direction == Directions.INIT){
            nextGameBoard = prevGameBoard;
            System.out.println("TEST");
        }else {
            nextGameBoard = getArrayForTesting();
        }

         */

        //Falls aus einem grund kein Update mehr kommt darf auch nichtsmehr passieren -> sonst gibts einen Exception
        int testForDuplicateCounter = 0;
        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                if (prevGameBoard[j][k] == nextGameBoard[j][k]) {
                    testForDuplicateCounter++;
                }
            }
        }
        if (testForDuplicateCounter == 16) {
            return;
        }

        //System.out.println("Break");

        //Itteriert über das Spielfeld
        for (int posX = 0; posX < tileCount; posX++) {
            for (int posY = 0; posY < tileCount; posY++) {

                //Fall wenn keine preTiles da sind -> Generiert ein neues Element
                if (nextGameBoard[posX][posY] != null && nextGameBoard[posX][posY].checkForPreTiles()) {

                    //Sucht im letzten Spielfeld nach dem Tile
                    for (int r = 0; r < tileCount; r++) {
                        for (int s = 0; s < tileCount; s++) {
                            if (nextGameBoard[posX][posY] == prevGameBoard[r][s]) {
                                //make transition
                                transition(nextGameBoard[posX][posY], posX, posY, false, 0, null);

                                newTileFlag = false;
                                break;
                            }
                        }
                    }

                    //Wenn im letzten Spielfeld kein Tile gefunden wurde wird hier ein neues erzeugt
                    if (newTileFlag) {

                        //Add new Tile

                        Pane tilePane = nextGameBoard[posX][posY].getPane();

                        tilePane.setLayoutX(gameBoardGap * (posX + 1) + (tileSize * posX));
                        tilePane.setLayoutY(gameBoardGap * (posY + 1) + (tileSize * posY));


                        pane.getChildren().add(tilePane);

                        zoomInTile(nextGameBoard[posX][posY]);

                    } else {
                        newTileFlag = true;
                    }
                }
                //Fall preTiles vorhanden sind -> animation der preTiles und darstellen des neuen Tiles
                else if (nextGameBoard[posX][posY] != null) {

                    //TODO: PROBLEM DIE TILES WERDEN SCHON VOR ENEDE DER TRANSITION AUF NULL GESETZT UND DIE PANES KÖNNE NICHT GELÖSCHT WERDEN
                    //MUSS IRGENDWIE DAS PROGRAMM PAUSIEREN ODER ERST NACHE DER TRANSITION LÖSCHEN

                    /**
                     * if (!field[j][k].checkForPreTiles()) {
                     *       field[j][k].clearPreTiles();
                     *}
                     *
                     * Das vllt von Gameboard  -> move , auf nach die animation verschieben, aber habe jz keine Lust mehr
                     *
                     */


                    transition(nextGameBoard[posX][posY].getPreFieldA(), posX, posY, true, 0, null);

                    transition(nextGameBoard[posX][posY].getPreFieldB(), posX, posY, true, 1, nextGameBoard[posX][posY]);
                }
            }
        }

        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                prevGameBoard[j][k] = nextGameBoard[j][k];
            }
        }


        //Gewinnabfrage
        checkForWin();
    }

    /**
     * Führt die Animation des Tiles aus und generiert ggf. danach ein neues Tile und  löscht das alte
     *
     * @param tile                      Tile welches die Animation ausführt
     * @param posX                      Zielposition in X
     * @param posY                      Zielposition in Y
     * @param deleteAfterTransitionFlag Falls wahr wird das Tile nach der Animation gelöscht
     * @param tielToCreate              Das Tile welches nach der Animation dergestellt werden soll
     */
    private void transition(Tile tile, int posX, int posY, boolean deleteAfterTransitionFlag, int x, Tile tielToCreate) {

        TranslateTransition translateTransition = new TranslateTransition();

        Pane tilePane = tile.getPane();

        translateTransition.setToX((gameBoardGap * (posX + 1) + (tileSize * posX)) - tilePane.getLayoutX());
        translateTransition.setToY((gameBoardGap * (posY + 1) + (tileSize * posY)) - tilePane.getLayoutY());
        translateTransition.setNode(tilePane);
        translateTransition.setDuration(new Duration(100));
        translateTransition.play();


        if (deleteAfterTransitionFlag && x == 0) {
            System.out.println("Delete 1");
            System.out.println("X:" + tile.getPosX() + "  Y:" + tile.getPosY());
            removeTile(tile);
            //translateTransition.setOnFinished(a -> removeTile(tile));
        }
        if (deleteAfterTransitionFlag && x == 1) {
            System.out.println("Delete 2");
            System.out.println("X:" + tile.getPosX() + "  Y:" + tile.getPosY());
            removeTile(tile);
            //translateTransition.setOnFinished(a -> removeTile(tile));
        }


        if (tielToCreate != null) {
            translateTransition.setOnFinished(a -> createTile(tielToCreate, posX, posY));
        }
    }

    /**
     * Generiet ein neues Tile nach der Animation der preTiles
     *
     * @param tile Tile welches dargestellt wird
     * @param posX Position in X an der es dargestellt wird
     * @param posY Position in Y an der es dagestellt wird
     */
    private void createTile(Tile tile, int posX, int posY) {

        tile.getPane().setLayoutX(gameBoardGap * (posX + 1) + (tileSize * posX));
        tile.getPane().setLayoutY(gameBoardGap * (posY + 1) + (tileSize * posY));

        pane.getChildren().add(tile.getPane());

        zoomInTile(tile);
    }


    /**
     * Entfernt das Tile bzw die Pane vom grafischen Spielfeld
     *
     * @param tile Zu entfernendes Tile
     */
    private void removeTile(Tile tile) {
        System.out.println("\nRemove Tile");
        System.out.println("X:" + tile.getPosX() + "  Y:" + tile.getPosY());
        pane.getChildren().remove(tile.getPane());

    }


    /**
     * Tiles für einen Zoomefeckt um 10% vergrößern
     *
     * @param tile Tile auf welches der Effeckt angewendet wird
     */
    private void zoomInTile(Tile tile) {

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(new Duration(50));
        scaleTransition.setNode(tile.getPane());
        scaleTransition.setByX(0.1f);
        scaleTransition.setByY(0.1f);
        scaleTransition.play();

        scaleTransition.setOnFinished(a -> zoomOutTile(tile));
    }

    /**
     * Tiles für einen Zoomefeckt auf Standart größe verkleinern
     *
     * @param tile Tile auf welches der Effeckt angewendet wird
     */
    private void zoomOutTile(Tile tile) {

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(new Duration(50));
        scaleTransition.setNode(tile.getPane());
        scaleTransition.setByX(-0.1f);
        scaleTransition.setByY(-0.1f);
        scaleTransition.play();
    }

    /**
     * Gewinnabrgrage -> Wenn gewonnen zeige Siegerbildschirm
     */
    private void checkForWin() {

        //TODO: GEWINNABFRAGE
        //Wenn das erste Mal gewonnen wurde wird der Gewinnerscreen angezeigt
        if (false&& winScreen == 0) { //TODO

            StackPane winnerPane = new StackPane();
            winnerPane.setPrefHeight(gameBoardSize);
            winnerPane.setPrefWidth(gameBoardSize);
            winnerPane.setId("winPane");
            winnerPane.toFront();
            pane.getChildren().add(winnerPane);

            Text winText = new Text("Gewonnen!");
            winText.setId("winText");
            winnerPane.getChildren().add(winText);

            winScreen++;
        }
        //Wenn gewonnen wurde aber man noch weiterspielt wird der Gewinnerscreen wieder entfernt
        else if (winScreen == 1) {
            pane.getChildren().remove(scene.lookup("#winPane"));
            pane.getChildren().remove(scene.lookup("#winText"));
        }
    }

    /**
     * Setzt den KiMode, kommt vom Main menü
     *
     * @param kiMode Der zu setzende Modus
     */
    @Override
    public void setKiMode(boolean kiMode) {
        this.kiMode = kiMode;
    }

    /**
     * Setze die anzahl der Tiles, kommt vom Main menü
     *
     * @param tileCount Anzahl der Tiles
     */
    @Override
    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    /**
     * Setzt den Higscore, wird beim Fensteraufruf gesetzt
     *
     * @param highscore Highscore
     */
    @Override
    public void setHighscore(int highscore) {
        highscoreLabel.setText(Integer.toString(highscore));
    }


    /**
     * Setzt den Score, wird nach jedem Zug aufgerufen
     *
     * @param score Score
     */
    @Override
    public void setScoreLabel(int score) {
        scoreLabel.setText(Integer.toString(score));
    }

    @Override
    public void setGameMode(GameModes gameMode) {
        this.gameMode = gameMode;
    }

    public void setLabel() {
        if (kiMode) {
            myLabel.setText("KI -> ON!!!");
        } else {
            myLabel.setText("KI -> OFF!!!");
        }
    }

    /**
     * Bekommt die Fenster größe von der Vorgänger Methode
     *
     * @param windowWidth     Fensterhöhe
     * @param windowHeight    Fensterbreite
     * @param minWindowWidth  Maximale Fensterbreite
     * @param minWindowHeight Maximale Fensterhöhe
     */
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = windowHeight;
    }

    //KANN später gelöscht werden nur zum Testen (oder in das Test Projekt verschben werden)
    //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
    //Erzeugt manuel ein Spielfeld Array
    public Tile[][] getArrayForTesting1() {

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

    //KANN später gelöscht werden nur zum Testen (oder in das Test Projekt verschben werden)
    //TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTEST
    //Erzeugt manuel ein Spielfeld Array
    public Tile[][] getArrayForTesting() {

        if (variable == 0) {

            System.out.println("Get First Array");
            testBoard[0][0] = new Tile(2, null, null, 0, 0);
            testBoard[3][0] = new Tile(2, null, null, 3, 0);
        }
        if (variable == 1) {
            System.out.println("Get Second Array");
            testBoard[0][2] = testBoard[0][0];
            testBoard[3][3] = testBoard[3][0];

            testBoard[0][2].setPosition(0, 2);
            testBoard[3][3].setPosition(3, 3);

            testBoard[0][0] = null;
            testBoard[3][0] = null;

            testBoard[0][0] = new Tile(2, null, null, 0, 0);
        }
        if (variable == 2) {
            System.out.println("Get third Array");

            Tile tile = new Tile(4, testBoard[0][2], testBoard[0][0], 0, 3);

            testBoard[0][0] = null;
            testBoard[0][2] = null;

            testBoard[0][3] = tile;

            testBoard[1][2] = new Tile(4, null, null, 1, 2);
        }

        variable++;
        return testBoard;
    }
}
