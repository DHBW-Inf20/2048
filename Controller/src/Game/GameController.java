package Game;

import Game.DataClasses.Directions;
import Game.DataClasses.GameModes;
import Game.DataClasses.Tile;
import Game.TileCreator.CooperativeTileCreator;
import Game.TileCreator.ITileCreator;
import Game.TileCreator.MinMaxTileCreator;
import Game.TileCreator.RandomTileCreator;


import java.util.Observable;

public class GameController implements IGameController {
    private GameModes gameMode = GameModes.random;
    private boolean aiEnabled = false;
    private ITileCreator iTileCreator;
    private final TileChangeListener tileChangeListener;
    private Tile[][] field;
    private int dimensions;


    public GameController(TileChangeListener tileChangeListener) {
        this.tileChangeListener = tileChangeListener;
    }


    @Override
    public void startGame(GameModes mode, boolean aiEnabled, int dimension) {
        this.gameMode = mode;
        this.aiEnabled = aiEnabled;
        this.dimensions = dimension;

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
        }

        field = iTileCreator.generateField(dimension);

        field = iTileCreator.generateNewNumber(field);
        field = iTileCreator.generateNewNumber(field);

        tileChangeListener.change(field);
    }

    @Override
    public void makeMove(Directions direction) {


        //TODO: Kommentieren
        //TODO: SCORE
        int score = 0;

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
                        if (field[k][j] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != 0 && tempField[tempFieldPosition - 1][j].getNumber() == field[k][j].getNumber() && !sumUpLast) {

                                int newNumber = (tempField[tempFieldPosition - 1][j].getNumber() * 2);

                                //Verdopple letztes Element
                                tempField[tempFieldPosition - 1][j] = new Tile(newNumber, tempField[tempFieldPosition - 1][j], field[k][j], tempFieldPosition - 1, j);
                                score = score + tempField[tempFieldPosition - 1][j].getNumber();
                                sumUpLast = true;

                            } else {
                                //Füge neues Element ein und counter++
                                tempField[tempFieldPosition][j] = field[k][j];
                                tempFieldPosition++;
                                sumUpLast = false;

                                if (!field[k][j].checkForPreTiles()) {
                                    field[k][j].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = 0;
                }

                break;


            case RIGHT:
                //Verschieben nach rechts

                tempFieldPosition = field.length - 1;
                sumUpLast = false;

                for (int j = 0; j < field.length; j++) {
                    for (int k = field.length - 1; k >= 0; k--) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (field[k][j] != null) {

                            System.out.println("j" + j + "k" + k);

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != field.length - 1 && tempField[tempFieldPosition + 1][j].getNumber() == field[k][j].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element

                                int newNumber = (tempField[tempFieldPosition + 1][j].getNumber() * 2);

                                //Verdopple letztes Element
                                System.out.println("NEW TILE RIGHT");
                                tempField[tempFieldPosition + 1][j] = new Tile(newNumber, tempField[tempFieldPosition + 1][j], field[k][j], tempFieldPosition + 1, j);

                                score = score + tempField[tempFieldPosition + 1][j].getNumber();
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[tempFieldPosition][j] = field[k][j];
                                tempFieldPosition--;
                                sumUpLast = false;
                                if (!field[k][j].checkForPreTiles()) {
                                    field[k][j].clearPreTiles();
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

                for (int j = 0; j < field.length; j++) {
                    for (int k = 0; k < field.length; k++) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (field[j][k] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != 0 && tempField[j][tempFieldPosition - 1].getNumber() == field[j][k].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element
                                int newNumber = (tempField[j][tempFieldPosition - 1].getNumber() * 2);

                                tempField[j][tempFieldPosition - 1] = new Tile(newNumber, tempField[j][tempFieldPosition - 1], field[j][k], tempFieldPosition - 1, k);
                                score = score + tempField[j][tempFieldPosition - 1].getNumber();
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[j][tempFieldPosition] = field[j][k];
                                tempFieldPosition++;
                                sumUpLast = false;
                                if (!field[j][k].checkForPreTiles()) {
                                    field[j][k].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = 0;
                }
                break;

            case DOWN:
                //Verschieben nach rechts
                tempFieldPosition = field.length - 1;
                sumUpLast = false;

                for (int j = 0; j < field.length; j++) {
                    for (int k = field.length - 1; k >= 0; k--) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (field[j][k] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != field.length - 1 && tempField[j][tempFieldPosition + 1].getNumber() == field[j][k].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element
                                int newNumber = (tempField[j][tempFieldPosition + 1].getNumber() * 2);

                                tempField[j][tempFieldPosition + 1] = new Tile(newNumber, tempField[j][tempFieldPosition + 1], field[j][k], tempFieldPosition + 1, k);
                                score = score + tempField[j][tempFieldPosition + 1].getNumber();
                                sumUpLast = true;
                            } else {
                                //Füge neues Element ein und counter++
                                tempField[j][tempFieldPosition] = field[j][k];
                                tempFieldPosition--;
                                sumUpLast = false;
                                if (!field[j][k].checkForPreTiles()) {
                                    field[j][k].clearPreTiles();
                                }
                            }
                        }
                    }
                    tempFieldPosition = field.length - 1;
                }
                break;

            default:
                break;
        }

        //Setze das board
        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {

                field[j][k] = tempField[j][k];
            }
        }

        field = iTileCreator.generateNewNumber(field);
        tileChangeListener.change(field);
    }
}

