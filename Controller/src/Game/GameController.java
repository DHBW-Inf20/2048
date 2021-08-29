package Game;

import DataClasses.Directions;
import DataClasses.GameModes;
import DataClasses.GameOptions;
import DataClasses.Tile;
import Game.Listeners.ScoreChangeListener;
import Game.Listeners.TileChangeListener;
import Game.TileCreator.*;

public class GameController implements IGameController {
    private GameModes gameMode = GameModes.random;
    private boolean aiEnabled = false;
    private ITileCreator iTileCreator;
    private TileChangeListener tileChangeListener;
    private Tile[][] field;
    private int dimensions;
    private ScoreChangeListener scoreChangeListener;

    //Für Variables Spielfeld
    private int tileCount;
    private double tileSize;

    private int score = 0;

    //gameStatus Im Spiel -> 0 | Wenn Gewonnen -> 1 | Nach gewinn weiterspielen -> 2 | Verloren -> 3
    private int gameStatus = 0;

    public GameController() {
    }

    public void setTileChangeListener(TileChangeListener tileChangeListener)
    {
        this.tileChangeListener = tileChangeListener;
    }

    @Override
    public void setScoreChangeListener(ScoreChangeListener scoreChangeListener)
    {
        this.scoreChangeListener = scoreChangeListener;
    }

    /**
     *
     *
     * @param tileSize größe der einzelnen Tiles
     * @param tileCount Spielfeldgröße (4,5,6,7,8,9,10)
     */
    public void setTileSize(double tileSize, int tileCount)
    {
        this.tileSize = tileSize;
        this.tileCount = tileCount;
    }


    /**
     * Initalisiert den GameControler mit dem ausgewählten TileCreator
     *
     * @param gameOptions
     */
    @Override
    public void startGame(GameOptions gameOptions) {
        this.gameMode = gameOptions.getGameMode();
        this.aiEnabled = gameOptions.isAiEnabled();
        this.dimensions = gameOptions.getFieldDimensions();

        switch (gameMode) {

            case random -> {
                iTileCreator = new RandomTileCreator();
            }
            case cooperative -> {
                iTileCreator = new CooperativeTileCreator();
            }
            case minMax -> {
                iTileCreator = new MinMaxTileCreator();
            }
            case minMaxCooperative -> {
                iTileCreator = new MinMaxTileCreatorCooperative();
            }
        }

        field = iTileCreator.generateField(dimensions);

        field = iTileCreator.generateNewNumber(field, tileSize, tileCount);
        field = iTileCreator.generateNewNumber(field, tileSize, tileCount);

        tileChangeListener.change(field);

    }


    /**
     *
     *  Führt den Zug in die gegebene Richtung aus in dem Lokalen Array das vom UI später abgefragt wird
     *
     * @param direction Richtung des Spielzuges
     */
    @Override
    public void makeMove(Directions direction) {

        Tile[][] tempField = updateField(direction, field); // ausgelagert, da ein Teil für die AI benötigt wird

        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {

                field[j][k] = tempField[j][k];
            }
        }

        //Checkt das Spielfeld auf gewonne/verloren
        checkGameStatus();
        if(gameStatus != 3 && gameStatus != 4){
            field = iTileCreator.generateNewNumber(field, tileSize, tileCount);
            tileChangeListener.change(field);
        }

    }


    public Tile[][] updateField(Directions direction, Tile[][] virtualField)
    {
        int tempFieldPosition;

        Tile[][] tempField = new Tile[dimensions][dimensions];
        boolean sumUpLast = false;

        switch (direction) {
            case LEFT:
                //Verschieben nach links
                tempFieldPosition = 0;

                for (int j = 0; j < field.length; j++) {
                    for (int k = 0; k < field.length; k++) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (virtualField[k][j] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != 0 && tempField[tempFieldPosition - 1][j].getNumber() == virtualField[k][j].getNumber() && !sumUpLast) {

                                int newNumber = (tempField[tempFieldPosition - 1][j].getNumber() * 2);

                                //Verdopple letztes Element
                                tempField[tempFieldPosition - 1][j] = new Tile(newNumber, tempField[tempFieldPosition - 1][j], virtualField[k][j], tempFieldPosition - 1, j, tileSize, tileCount);
                                score = score + tempField[tempFieldPosition - 1][j].getNumber();
                                scoreChangeListener.change(score);
                                sumUpLast = true;

                            } else {
                                //Füge neues Element ein und counter++
                                tempField[tempFieldPosition][j] = virtualField[k][j];
                                tempFieldPosition++;
                                sumUpLast = false;

                                if (!virtualField[k][j].checkForPreTiles()) {
                                    virtualField[k][j].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = 0;
                }
                break;

            case RIGHT:
                //Verschieben nach rechts

                tempFieldPosition = virtualField.length - 1;
                sumUpLast = false;

                for (int j = 0; j < virtualField.length; j++) {
                    for (int k = virtualField.length - 1; k >= 0; k--) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (virtualField[k][j] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != virtualField.length - 1 && tempField[tempFieldPosition + 1][j].getNumber() == virtualField[k][j].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element

                                int newNumber = (tempField[tempFieldPosition + 1][j].getNumber() * 2);

                                //Verdopple letztes Element
                                tempField[tempFieldPosition + 1][j] = new Tile(newNumber, tempField[tempFieldPosition + 1][j], virtualField[k][j], tempFieldPosition + 1, j, tileSize, tileCount);
                                score = score + tempField[tempFieldPosition + 1][j].getNumber();
                                scoreChangeListener.change(score);
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[tempFieldPosition][j] = virtualField[k][j];
                                tempFieldPosition--;
                                sumUpLast = false;

                                if (!virtualField[k][j].checkForPreTiles()) {
                                    virtualField[k][j].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = field.length - 1;
                }
                break;

            case UP:
                //Verschieben nach oben
                tempFieldPosition = 0;
                sumUpLast = false;

                for (int j = 0; j < virtualField.length; j++) {
                    for (int k = 0; k < virtualField.length; k++) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (virtualField[j][k] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != 0 && tempField[j][tempFieldPosition - 1].getNumber() == virtualField[j][k].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element
                                int newNumber = (tempField[j][tempFieldPosition - 1].getNumber() * 2);

                                tempField[j][tempFieldPosition - 1] = new Tile(newNumber, tempField[j][tempFieldPosition - 1], virtualField[j][k], tempFieldPosition - 1, k, tileSize, tileCount);
                                score = score + tempField[j][tempFieldPosition - 1].getNumber();
                                scoreChangeListener.change(score);
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[j][tempFieldPosition] = virtualField[j][k];
                                tempFieldPosition++;
                                sumUpLast = false;

                                if (!virtualField[j][k].checkForPreTiles()) {
                                    virtualField[j][k].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = 0;
                }
                break;

            case DOWN:
                //Verschieben nach rechts
                tempFieldPosition = virtualField.length - 1;
                sumUpLast = false;

                for (int j = 0; j < virtualField.length; j++) {
                    for (int k = virtualField.length - 1; k >= 0; k--) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (virtualField[j][k] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != field.length - 1 && tempField[j][tempFieldPosition + 1].getNumber() == virtualField[j][k].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element
                                int newNumber = (tempField[j][tempFieldPosition + 1].getNumber() * 2);

                                tempField[j][tempFieldPosition + 1] = new Tile(newNumber, tempField[j][tempFieldPosition + 1], virtualField[j][k], tempFieldPosition + 1, k, tileSize, tileCount);
                                score = score + tempField[j][tempFieldPosition + 1].getNumber();
                                scoreChangeListener.change(score);
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[j][tempFieldPosition] = virtualField[j][k];
                                tempFieldPosition--;
                                sumUpLast = false;

                                if (!virtualField[j][k].checkForPreTiles()) {
                                    virtualField[j][k].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = virtualField.length - 1;
                }
                break;

            default:
                break;
        }
        return tempField;
    }


    /**
     * Überprüft das Spielfeld auf gewonnen oder Verloren
     *
     */
    private void checkGameStatus(){

        int tileCountForLose = 0;

        //Wenn schon gewonnen dan setzte auf "Gewonnen aber weiterspielen"
        if(gameStatus == 1){
            gameStatus = 2;
        }
        //Wenn im Spiel prüfe auf gewinn
        if(gameStatus == 0){
            //Check ob Gewonne
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field.length; k++) {
                    if(field[j][k] != null && field[j][k].getNumber() == 1024){
                        gameStatus = 1;
                    }
                }
            }
        }

        // Wenn schon verloren setzte status auf 4 -> undefieniert, mache nichts mehr
        if(gameStatus == 3){
            gameStatus++;
        }
        // Wenn im Spiel oder "gewonnen aber weiterspielen" prüfe auf verloren
        if(gameStatus == 0 || gameStatus == 2){
            //Check ob Verloren
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field.length; k++) {
                    if(field[j][k] != null){
                        tileCountForLose++;
                    }
                }
            }
            if(tileCountForLose >= (tileCount*tileCount) - 1 && !checkForNeighbours()){
                gameStatus = 3;
            }
        }
    }

    private boolean checkForNeighbours(){

        int prevField = 0;
        int actualField = 0;

        //horizontaler durchgang
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {
                if(field[j][k] != null)
                    actualField = field[j][k].getNumber();

                if(actualField == prevField){
                    return true;
                }
                prevField = actualField;
            }
            prevField = 0;
            actualField = 0;
        }

        //Vertikaler durchgang
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {
                if(field[k][j] != null)
                    actualField = field[k][j].getNumber();

                if(actualField == prevField){
                    return true;
                }
                prevField = actualField;
            }
            prevField = 0;
            actualField = 0;
        }
        return false;
    }

    /**
     * Gibt den Status des Spiels zurück
     *
     * @return Status des Spiels
     */
    public int getGameStatus(){
        return gameStatus;
    }
}

