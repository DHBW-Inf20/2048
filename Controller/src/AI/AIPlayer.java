package AI;

import DataClasses.Directions;
import DataClasses.Tile;
import Game.GameController;
import Game.TileCreator.RandomTileCreator;

import java.util.*;

import static java.lang.Integer.valueOf;

public class AIPlayer implements IAIPlayer
{
    private int rounds = 100;
    private int standardDepth = 4;

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
        int up, down, left, right, max;

        for(int i = 0; i < rounds; i++)
        {
            up = getScore(field, Directions.UP, standardDepth);
            down = getScore(field, Directions.DOWN, standardDepth);
            left = getScore(field, Directions.LEFT, standardDepth);
            right = getScore(field, Directions.RIGHT, standardDepth);
            max = Math.max(Math.max(up, down), Math.max(left, right));
            if(max == up) appearances.put(Directions.UP, appearances.get(Directions.UP)+up);
            if(max == down) appearances.put(Directions.DOWN, appearances.get(Directions.DOWN)+down);
            if(max == left) appearances.put(Directions.LEFT, appearances.get(Directions.LEFT)+left);
            if(max == right) appearances.put(Directions.RIGHT, appearances.get(Directions.RIGHT)+right);
        }
        // Findet die maximale Value und gibt zugehörigen Key (also eine Direction) zurück
        return Collections.max(appearances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    /**
     * @param field Kopiertes Spielfeld vor nächstem Zug (damit nur Value und nicht Refernce übergeben wird)
     * @param direction Richtung des nächsten Zuges, der hier ausgeführt werden soll
     * @param aDepth Tiefe der Rekursion
     * @return (Maximale) Anzahl der freien Felder
     */
    @Override
    public int getScore(Tile[][] field, Directions direction, int aDepth)
    {
        Tile[][] newField = field.clone();
        GameController aiController = new GameController();
        newField = aiController.updateField(direction, newField);
        int depth =  valueOf(aDepth);

        if(depth == 0)
        {
            return getFreeTiles(newField);
        }
        else
        {
            // Kombination aus drei Math.max, da jeweils nur zwei Integer vergleichen werden können -> Verkettung
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
