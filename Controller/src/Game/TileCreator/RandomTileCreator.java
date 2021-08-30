package Game.TileCreator;

import DataClasses.Tile;
import Utilities.Utilities;

import java.util.Random;

public class RandomTileCreator implements ITileCreator
{
    private Random random = new Random();
    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }


    @Override
    public Tile[][] generateNewNumber(Tile[][] inputField, double tileSize, int tileCount) {

        //Generiert eine zufällige Zahl zum bestimmen des nächsten Tiles
        int nextRandomNumber;

        int randomNumber = random.nextInt(10);

        //Wähle die nächste Zahl (10% -> 4 | 90% -> 2)
        if (randomNumber == 1) {
            nextRandomNumber = 4;
        } else {
            nextRandomNumber = 2;
        }
        //Parameter nicht ändern
        return insertNumberInField(Utilities.copyField(inputField), tileSize, tileCount, nextRandomNumber);
    }


    //Erzeugt Tile mit Nummer und fügt dieses an eine zufällige Stelle im Spielfeld ein
    public Tile[][] insertNumberInField(Tile[][] field, double tileSize, int tileCount, int number)
    {
        int randomNumber;
        //Schauen wie viele freie Felder es gibt
        int freeFields = 0;
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {
                //Bei einem freien Feld wird die laufvariable hochgezählt
                if (field[j][k] == null) {
                    freeFields++;
                }
            }
        }

        //Wenn keine freien Felder vorhanden, keine mehr erzeugen -> Verloren
        if(freeFields != 0) {

            randomNumber = random.nextInt(freeFields);
            int fieldCount = 0;

            //Die neue zahl in ein zufälliges freies Feld einsetzen
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field.length; k++) {

                    //Bei einem freien Feld wird die laufvariable hochgezählt
                    if (field[j][k] == null) {
                        //Ist das Feld frei und die laufvariable erreicht wird das Feld gesetzt
                        if (randomNumber == fieldCount) {
                            field[j][k] = new Tile(number, null, null, j, k, tileSize, tileCount);
                        }
                        fieldCount++;
                    }
                }
            }
        }

        return field;
    }


}
