package Game.TileCreator;

import DataClasses.Tile;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Die MinMaxTileCreator-Klasse implementiert den MINMAX-Algoorithmus um neu erscheinende Zahlen
 * auf dem Spielfeld möglichst ungünstig für den Spieler zu generieren.
 */
public class MinMaxTileCreatorCooperative implements ITileCreator
{
    /**
     * Variablen für Design
     */
    private int tileCount = 1;
    private double tileSize = 1;

    //Hier die gewünschte Suchtiefe setzen
    private int gewuenschteTiefe = 4;

    //klassenvariablen
    private Point2D gespeicherterZug;
    private int newTileNumber = 2; //gibt die Nummer des neuen Kärtchens an
    private  int dimensions;

    /**
     * generiert ein leeres Spielfeld
     * @param dimensions Dimensionen des Spielfeld
     * @return leeres Spielfeld
     */
    @Override
    public Tile[][] generateField(int dimensions)
    {
        this.dimensions = dimensions;
        return new Tile[dimensions][dimensions];
    }

    /**
     * generiert eine neue Nummer auf einem Spielfeld, möglichst
     * ungünstig für den Spieler
     * @param field das Spielfeld
     * @param tileSize Größe der Spielsteine
     * @param tileCount Anzahl der Spielsteine
     * @return spielfeld mit neuer Nummer / neuem Stein darauf
     */
    @Override
    public Tile[][] generateNewNumber(Tile[][] field, double tileSize, int tileCount) { //Hauptmethode
        dimensions = field.length;
        this.tileCount = tileCount;
        this.tileSize = tileSize;
        Tile[][] originalField = duplicateField(field);
        ramdomNewTileNumber();
        int bewertung = max(gewuenschteTiefe, originalField);
        if (gespeicherterZug == null || countFreeTiles(originalField) == 0){
            System.out.println("es gab keine weiteren Zuege mehr");
            return field;
        }
        System.out.println("Platziere Tile bei position " + gespeicherterZug.getX() +", " +gespeicherterZug.getY());
        return calculateTile(field, gespeicherterZug, newTileNumber); // führt gespeicherten Zug aus
    }

    /**
     * Methode, die den für den Spieler ungünstigsten Stein platziert.
     * Teil des MINMAX-Algorithmus
     * @param tiefe Suchtiefe
     * @param field Spielfeld
     * @return "Score" nach dem gefundenen zug
     */
    private int max(int tiefe, Tile[][] field) {
        if (tiefe == 0 || !checkTilePlaceable(field)){

            return countFreeTiles(field);
        }
        int maxWert = -99999;
        List<Point2D> possibleTiles = generatePossibleTiles(field);
        int possibleMovesCount = countFreeTiles(field);
        while (possibleMovesCount > 0) { //für jedes possibleTile durchlaufen
            Point2D newTilePosition =possibleTiles.get(possibleMovesCount-1); //eine position auswählen
            int newTileNumberThisMove = 2;
            if (tiefe == gewuenschteTiefe) {
                newTileNumberThisMove = newTileNumber;
            }
            Tile[][] newField = calculateTile(field, newTilePosition, newTileNumberThisMove); //an ausgewählte position platzieren
            int wert = min(tiefe-1, newField); //anderen Spieler spielen lassen
            newField = duplicateField(field);//macheZugRueckgaengig();
            if (wert > maxWert) {
                maxWert = wert;
                if (tiefe == gewuenschteTiefe)
                    gespeicherterZug = newTilePosition;
            }
            possibleMovesCount = possibleMovesCount-1;
        }
        return maxWert;
    }

    /**
     * Methode, die den günstigsten Move für den Spieler findet.
     * Gegenstück von max, auch teil des MINMAX-Algorithmus
     * @param tiefe Suchtiefe
     * @param field Spielfeld
     * @return "Score" nach dem gefundenen zug
     */
    private int min(int tiefe, Tile[][] field) {
        if (tiefe == 0 || !checkMovePossible(field)){
            return countFreeTiles(field);
        }

        int minWert = 99999;
        List<String> possibleMoves = generatePossibleMoves(field);
        int possibleMovesCount = possibleMoves.size();
        while (possibleMovesCount > 0) { //für jedes PossibleMoves durchlaufen
            // einen move auswählen
            String move = possibleMoves.get(possibleMovesCount-1);
            Tile[][] newField = calculateMove(field, move); //ausgewählten move durchführen
            int wert = max(tiefe-1, newField); //anderen spieler spielen lassen
            newField = duplicateField(field);//macheZugRueckgaengig(); //zug rückgängig machen
            if (wert < minWert) {
                minWert = wert;
            }
            possibleMovesCount--;
        }
        return minWert;
    }


    /**
     *  checks if MIN-Player has a possible move
     * @param field Spielfeld
     * @return true, if MIN-Player has a possible move, otherwise false
     */
    private boolean checkMovePossible(Tile[][] field){
        if(generatePossibleMoves(field) != null){
            return true;
        }
        return false;
    }

    /**
     * checks if MAX-Player has a possible move
     * @param field Spielfeld
     * @return true, if MAX-Player has a possible move, otherwise false
     */
    private boolean checkTilePlaceable(Tile[][] field){
        if(countFreeTiles(field) != 0){
            return true;
        }
        return false;
    }

    /**
     *  generiert die möglichen Felder, auf denen platziert werden kann.
     * @param field Spielfeld
     * @return Liste mit möglichen Feldern, auf denen platziert werden kann
     */
    private List<Point2D> generatePossibleTiles(Tile[][] field){ //
        List<Point2D> freeTiles = new ArrayList<Point2D>();
        for(int i=0; i<dimensions; i++){
            for(int j=0; j< dimensions; j++){
                if(field[i][j] == null){
                    freeTiles.add(new Point2D(i,j));
                }

            }
        }

        return freeTiles;
    }

    /**
     *  generiert die möglichen Richtungen, in die der Spieler wischen kann.
     * @param field Spielfeld
     * @return Liste mit möglichen Richtungen
     */
    private List<String> generatePossibleMoves(Tile[][] field){ //

        List<String> possibleMovesList = new ArrayList<String>(); //Mögliche Richtungen: UP, DOWN, LEFT, RIGHT
        possibleMovesList.add("UP");
        possibleMovesList.add("DOWN");
        possibleMovesList.add("LEFT");
        possibleMovesList.add("RIGHT");

        return possibleMovesList;
    }

    /**
     *  Calculates the Filed with a new Tile placed.
     * @param field Spielfeld
     * @param newTile neues Tile
     * @return neues Spielfeld
     */
    private  Tile[][] calculateTile(Tile[][] field, Point2D newTile, int tileNumber){//
        field[(int)newTile.getX()] [(int)newTile.getY()] = new Tile(tileNumber, null, null, (int)newTile.getX(), (int)newTile.getY(), tileSize, tileCount);
        return field;
    }

    /**
     *  calculates the Field after a Move
     * @param field Spielfeld
     * @param move Richtung, in die gespielt wird
     * @return neues Spielfeld
     */
    private Tile[][] calculateMove(Tile[][] field, String move){ //calculates the Field after a Move // Nicht ausführbare moves müssen erkannt und unterbunden werden (Moves bei denne keine Tile verschoben wird z.B. alle am Rand) -> kontrolle des Spielfeld??

        int tempFieldPosition;

        Tile[][] tempField = new Tile[dimensions][dimensions];
        boolean sumUpLast = false;

        switch (move) {
            case "LEFT":
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
                                tempField[tempFieldPosition - 1][j] = new Tile(newNumber, tempField[tempFieldPosition - 1][j], field[k][j], tempFieldPosition - 1, j, tileSize, tileCount);

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

            case "RIGHT":
                //Verschieben nach rechts

                tempFieldPosition = field.length - 1;
                sumUpLast = false;

                for (int j = 0; j < field.length; j++) {
                    for (int k = field.length - 1; k >= 0; k--) {

                        //Wenn auf ein nicht leeres Feld gestoßen wird
                        if (field[k][j] != null) {

                            //Prüfe ob Vorgänger gleich
                            if (tempFieldPosition != field.length - 1 && tempField[tempFieldPosition + 1][j].getNumber() == field[k][j].getNumber() && !sumUpLast) {
                                //Verdopple letztes Element

                                int newNumber = (tempField[tempFieldPosition + 1][j].getNumber() * 2);

                                //Verdopple letztes Element
                                tempField[tempFieldPosition + 1][j] = new Tile(newNumber, tempField[tempFieldPosition + 1][j], field[k][j], tempFieldPosition + 1, j, tileSize, tileCount);

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

            case "UP":
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

                                tempField[j][tempFieldPosition - 1] = new Tile(newNumber, tempField[j][tempFieldPosition - 1], field[j][k], tempFieldPosition - 1, k, tileSize, tileCount);

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

            case "DOWN":
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

                                tempField[j][tempFieldPosition + 1] = new Tile(newNumber, tempField[j][tempFieldPosition + 1], field[j][k], tempFieldPosition + 1, k, tileSize, tileCount);

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

        //setzt die einzelnen Werte von nextGameBoard in prevGameBoard
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {
                field[j][k] = tempField[j][k];
            }
        }
        return field;
    }

    /**
     * Zählt die freien Felder auf dem Spielfeld.
     * Score des MAX-Spielers
     * @param field Spielfeld
     * @return Anzahl freie Felder auf dem Spielfeld
     */
    private int countFreeTiles(Tile[][] field){ //Score des MAX-Spielers
        List<Point2D> freeTiles = generatePossibleTiles(field);
        return freeTiles.size();
    }

    /**
     * Dupliziert das Spielfeld.
     * @param field Spielfeld
     * @return Klon des Spielfelds
     */
    private Tile[][] duplicateField(Tile[][]field){
        Tile[][] newField = new Tile[dimensions][dimensions];
        for(int i=0; i<dimensions; i++){
            for(int j=0; j<dimensions; j++){
                if(field[i][j] != null){
                    newField[i][j] = new Tile(field[i][j].getNumber(), field[i][j].getPreFieldA(), field[i][j].getPreFieldB(), field[i][j].getPosX(), field[i][j].getPosY(), tileSize, tileCount);
                }
            }
        }
        return  newField;
    }

    /**
     * Methode für alternative Implementation des MINMAX-Algorithmus. Hier nicht benötigt.
     * Score des MIN-Spielers
     * @param field
     * @return
     */
    private int calculateScore(Tile[][] field){ //Score des MIN-Spielers

        return 0;
    }

    /**
     * Setzt die Klassenvariable newTileNumber zu 10% auf eine 4 und zu 90% auf eine 2
     */
    private void ramdomNewTileNumber(){
        Random r = new Random();
        if( r.nextInt((10 - 1) + 1) + 1 == 5){
            this.newTileNumber = 4;
        } else {
            this.newTileNumber = 2;
        }
    }


}
