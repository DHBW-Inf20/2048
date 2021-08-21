package Game.TileCreator;

import DataClasses.Tile;
import javafx.util.Pair;

import java.util.Random;

public class CooperativeTileCreator implements ITileCreator
{
    private final RandomTileCreator randomTileCreator = new RandomTileCreator();
    private final Random random = new Random();


    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }


    @Override
    public Tile[][] generateNewNumber(Tile[][] inputField, double tileSize, int tileCount)
    {


        Tile[][] field = inputField.clone();

        int number = random.nextInt(10)==1?4:2;

        Pair<Integer, Integer> optimalPosition = findOptimalPositionForTileInsertion(field,number); //Versuche eine optimale Position für ein Spielstein mit dem Wert von 'number' zu finden
        if(optimalPosition==null) // Es kann keine optimale Position ermittelt werden (alle Positionen belegt)
        {
            return randomTileCreator.insertNumberInField(field,tileSize,tileCount,number); //Spielstein wird an zufälliger Stelle im Feld plaziert
        }

        field[optimalPosition.getKey()][optimalPosition.getValue()] = new Tile(number,null,null,optimalPosition.getKey(),optimalPosition.getValue(),tileSize,tileCount);
        return field;
    }


    /**
     * @param field Das Spielfeld aus Tiles
     * @param number Die Nummer des Spielsteins, für den eine optimale Plazierung gefunden werden soll
     * @return Ein Pair -> Erstes Element: X-Position, Zweites Element: Y-Position
     */


    private Pair<Integer,Integer> findOptimalPositionForTileInsertion(Tile[][] field, int number)
    {
        for (int i = 0; i < field.length; i++)
        {
            for (int j = 0; j < field[i].length; j++)
            {
                if (field[i][j] ==null||field[i][j].getNumber() != number) //Einen Spielstein mit der nummer 'number suchen
                    continue;

                if (field.length > i + 1 && field[i + 1][j] == null)  //Schauen, ob oberhalb vom gefundenen Spielstein ein Spielstein mit der gleichen Nummer plaziert werden kann
                    return new Pair<>(i + 1, j);
                if (i - 1 > 0 && field[i - 1][j] == null) //Schauen, ob unterhalb gefundenen Spielstein ein Spielstein mit der gleichen Nummer plaziert werden kann
                    return new Pair<>(i - 1, j);
                if (field[i].length > j + 1 && field[i][j + 1] == null) // Schauen, ob rechts vom gefundenen Spielstein ein Spielstein mit der gleichen Nummer plaziert werden kann
                    return new Pair<>(i, j + 1);
                if (j - 1 > 0 && field[i][j - 1] == null) //Schauen, ob links vom gefundenen Spielstein ein Spielstein mit der gleichen Nummer plaziert werden kann
                    return new Pair<>(i, j - 1);
            }
        }
        return null; //Es konnte keine optimale Position zur Plazierung eines Spielsteins mit der Nummer 'number' gefunden werden
    }

}
