package AI;

import DataClasses.Directions;
import DataClasses.Tile;
import Game.GameController;
import Game.TileCreator.RandomTileCreator;

import java.util.*;

public class AIPlayer implements IAIPlayer
{
    /**
     * @param field Aktuelles Spielfeld vor nächstem Zug
     * @return Richtung (Rechts, Links, Hoch, Runter)
     */
    @Override
    public Directions calculateNextDirection(Tile[][] field)
    {
        Map<Directions,Integer> appearances = new HashMap<>();
        appearances.put(Directions.UP,0);
        appearances.put(Directions.DOWN,0);
        appearances.put(Directions.LEFT,0);
        appearances.put(Directions.RIGHT,0);
        int up, down, left, right;
        int max;

        for(int i = 0; i < 100; i++)
        {
            up = getScore(field, Directions.UP, 4);
            down = getScore(field, Directions.DOWN, 4);
            left = getScore(field, Directions.LEFT, 4);
            right = getScore(field, Directions.RIGHT, 4);
            max = Math.max(Math.max(up, down), Math.max(left, right));
            if(max == up) appearances.put(Directions.UP, appearances.get(Directions.UP)+1);
            if(max == down) appearances.put(Directions.DOWN, appearances.get(Directions.DOWN)+1);
            if(max == left) appearances.put(Directions.LEFT, appearances.get(Directions.LEFT)+1);
            if(max == right) appearances.put(Directions.RIGHT, appearances.get(Directions.RIGHT)+1);
        }
        return Collections.max(appearances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    /**
     * @param field Kopiertes Spielfeld vor nächstem Zug (damit nur Value und nicht Refernce übergeben wird)
     * @param direction Richtung des nächsten Zuges, der hier ausgeführt werden soll
     * @param depth Tiefe der Rekursion
     * @return (Maximale) Anzahl der freien Felder
     */
    @Override
    public int getScore(Tile[][] field, Directions direction, int depth)
    {
        Tile[][] newField = field.clone();
        GameController aiController = new GameController();
        newField = aiController.updateField(direction);

        if(depth == 0)
        {
            return getFreeTiles(newField);
        }
        else
        {
            return Math.max(Math.max(getScore(newField, Directions.UP, depth-1),
                                     getScore(newField, Directions.DOWN, depth-1)),
                            Math.max(getScore(newField, Directions.LEFT, depth-1),
                                     getScore(newField, Directions.RIGHT, depth-1)));
        }
    }

    /**
     * @param field Derzeitig getestete Feldkombination, welche in der GUI noch nicht angezeigt wird
     * @return Anzahl der freien Felder
     */
    @Override
    public int getFreeTiles(Tile[][] field)
    {
        int freeTiles = 0;
        for(int i = 0; i < field[0].length; i++)
        {
            for(int j = 0; j < field[0].length; j++)
            {
                if(field[i][j] == null) freeTiles++;
            }
        }
        return freeTiles;
    }

}
