package Game.TileCreator;

import Game.DataClasses.Tile;
import javafx.scene.effect.Light;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class CooperativeTileCreator implements ITileCreator
{
    private final RandomTileCreator randomTileCreator = new RandomTileCreator();
    private final Random random = new Random();

    private final int difficultyLevel  = 2; //Gibt an, wie häufig ein CooperativeTile generiert wird

    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }


    @Override
    public Tile[][] generateNewNumber(Tile[][] inputField, double tileSize, int tileCount)
    {

        if(random.nextInt(difficultyLevel)==0)
        {
            System.out.println("random tile");
       //     return randomTileCreator.generateNewNumber(field, tileSize, tileCount);
        }


        Tile[][] field = inputField.clone();


        int number = 4;

        Pair<Integer, Integer> optimalPosition = findOptimalPositionForTileInsertion(field,number); //Versuche eine optimale Position für das Tile mit der Nummer 4 zu finden
        if(optimalPosition==null){
            number = 2;
            optimalPosition = findOptimalPositionForTileInsertion(field,number); //Versuche eine optimale Position für das Tile mit der Nummer 2 zu finden
        }
        if(optimalPosition==null) //Es kann kein Cooperative-Tile generiert/gefunden werden
        {
            return randomTileCreator.insertNumberInField(field,tileSize,tileCount,4); //Tile mit Zahl 4 random plazieren (4 ist besser als 2)
        }

        System.out.println("cooperate tile: "+number);
        field[optimalPosition.getKey()][optimalPosition.getValue()] = new Tile(number,null,null,optimalPosition.getKey(),optimalPosition.getValue(),tileSize,tileCount);
        return field;
    }


    /**
     * @param field Das Spielfeld aus Tiles
     * @param insertTile Das Tile zudem ein passendes neues Tile eingefügt werden soll, dass es im nächsten Vorgang verschmolzen werden kann
     * @return Ein Pair -> Erstes Element: X-Position, Zweites Element: Y-Position
     */



    private Pair<Integer,Integer> findOptimalPositionForTileInsertion(Tile[][] field, int number)
    {
        for (int i = 0; i < field.length; i++)
        {
            for (int j = 0; j < field[i].length; j++)
            {
                if (field[i][j] ==null||field[i][j].getNumber() != number)
                    continue;


                if (field.length > i + 1 && field[i + 1][j] == null)
                    return new Pair<>(i + 1, j);
                if (i - 1 > 0 && field[i - 1][j] == null)
                    return new Pair<>(i - 1, j);
                if (field[i].length > j + 1 && field[i][j + 1] == null)
                    return new Pair<>(i, j + 1);
                if (j - 1 > 0 && field[i][j - 1] == null)
                    return new Pair<>(i, j - 1);
            }
        }
        return null;
    }

}
