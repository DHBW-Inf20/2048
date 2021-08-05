package Game.TileCreator;

import Game.DataClasses.Tile;

import java.util.Random;

public class RandomTileCreator implements ITileCreator
{

    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }

    @Override
    public Tile[][] generateNewNumber(Tile[][] field) {

        //Generiert eine zufällige Zahl zum bestimmen des nächsten Tiles
        int nextRandomNumber;
        Random random = new Random();
        int randomNumber = random.nextInt(10);

        //Wähle die nächste Zahl (10% -> 4 | 90% -> 2)
        if (randomNumber == 1) {
            nextRandomNumber = 4;
        } else {
            nextRandomNumber = 2;
        }

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

        randomNumber = random.nextInt(freeFields);
        int fieldCount = 0;

        //Die neue zahl in ein zufälliges freies Feld einsetzen
        for (int j = 0; j < field.length; j++) {
            for (int k = 0; k < field.length; k++) {

                //Bei einem freien Feld wird die laufvariable hochgezählt
                if (field[j][k] == null) {
                    //Ist das Feld frei und die laufvariable erreicht wird das Feld gesetzt
                    if (randomNumber == fieldCount) {
                        field[j][k] = new Tile(nextRandomNumber, null, null, j, k);
                    }
                    fieldCount++;
                }
            }
        }

        return field;
    }


}
