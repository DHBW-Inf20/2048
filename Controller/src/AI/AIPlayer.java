package AI;

import DataClasses.Directions;
import DataClasses.GameOptions;
import DataClasses.Tile;
import Game.GameController;
import Game.IGameController;

import java.util.*;

import static java.lang.Integer.valueOf;

public class AIPlayer implements IAIPlayer
{
    private IGameController aGameController;
    private int aRounds = 10;
    private int aStandardDepth = 4;

    public AIPlayer(GameOptions pGameOptions)
    {
        this.aGameController = new GameController();
        this.aGameController.startGame(pGameOptions);
    }

    /**
     * @param pField Aktuelles Spielfeld vor nächstem Zug
     * @return Richtung (Rechts, Links, Hoch, Runter)
     */
    @Override
    public Directions calculateNextDirection(Tile[][] pField)
    {
        Map<Directions,Integer> appearances = new HashMap<>();
        appearances.put(Directions.UP,0);
        appearances.put(Directions.DOWN,0);
        appearances.put(Directions.LEFT,0);
        appearances.put(Directions.RIGHT,0);
        int up, down, left, right, max;

        for(int i = 0; i < aRounds; i++)
        {
            up = getScore(pField, Directions.UP, aStandardDepth);
            down = getScore(pField, Directions.DOWN, aStandardDepth);
            left = getScore(pField, Directions.LEFT, aStandardDepth);
            right = getScore(pField, Directions.RIGHT, aStandardDepth);
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
     * @param pField Kopiertes Spielfeld vor nächstem Zug (damit nur Value und nicht Refernce übergeben wird)
     * @param pDirection Richtung des nächsten Zuges, der hier ausgeführt werden soll
     * @param pDepth Tiefe der Rekursion
     * @return (Maximale) Anzahl der freien Felder
     */
    @Override
    public int getScore(Tile[][] pField, Directions pDirection, int pDepth)
    {
        Tile[][] newField = pField.clone();
        newField = aGameController.calculateNewField(pDirection, newField);
        int depth =  valueOf(pDepth);

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
     * @param pField Derzeitig getestete Feldkombination, welche in der GUI noch nicht angezeigt wird
     * @return Anzahl der freien Felder
     */
    @Override
    public int getFreeTiles(Tile[][] pField)
    {
        int freeTiles = 0;
        for(int i = 0; i < pField[0].length; i++)
        {
            for(int j = 0; j < pField[0].length; j++)
            {
                if(pField[i][j] == null) freeTiles++;
            }
        }
        return freeTiles;
    }

}
