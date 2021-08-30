package AI;

import DataClasses.Directions;
import DataClasses.GameModes;
import DataClasses.GameOptions;
import DataClasses.Tile;
import Game.GameController;
import Game.IGameController;
import Game.TileCreator.*;
import Utilities.Utilities;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.remainderUnsigned;
import static java.lang.Integer.valueOf;

public class AIPlayer implements IAIPlayer
{
    private IGameController aGameController;
    private int aRounds = 100;
    private ITileCreator aTileCreator;
    private GameModes aGameMode;

    public AIPlayer(GameOptions pGameOptions)
    {
        this.aGameController = new GameController();
        this.aGameController.startGame(pGameOptions);
         aGameMode = pGameOptions.getGameMode();
         switch(aGameMode)
         {
             case random -> {
                 aTileCreator = new RandomTileCreator();
             }
             case cooperative -> {
                 aTileCreator = new CooperativeTileCreator();
             }
             case minMax -> {
                 aTileCreator = new MinMaxTileCreator();
             }
             case minMaxCooperative -> {
                 aTileCreator = new MinMaxTileCreatorCooperative();
             }
         }
    }



    /**
     * @param pField Aktuelles Spielfeld vor nächstem Zug
     * @return Richtung (Rechts, Links, Hoch, Runter)
     */
    @Override
    public Directions calculateNextDirection(Tile[][] pField)
    {

        System.out.println("ccc");

        Map<Directions,Integer> appearances = new HashMap<>();
        appearances.put(Directions.UP,0);
        appearances.put(Directions.DOWN,0);
        appearances.put(Directions.LEFT,0);
        appearances.put(Directions.RIGHT,0);
        int up, down, left, right;

        for(int i = 0; i < aRounds; i++)
        {
            up = getScore(pField, Directions.UP);
            down = getScore(pField, Directions.DOWN);
            left = getScore(pField, Directions.LEFT);
            right = getScore(pField, Directions.RIGHT);



            appearances.put(Directions.UP, appearances.get(Directions.UP)+up);
            appearances.put(Directions.DOWN, appearances.get(Directions.DOWN)+down);
            appearances.put(Directions.LEFT, appearances.get(Directions.LEFT)+left);
            appearances.put(Directions.RIGHT, appearances.get(Directions.RIGHT)+right);
        }
        // Findet die maximale Value und gibt zugehörigen Key (also eine Direction) zurück
        var direction =  Collections.max(appearances.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(direction);
        return direction;

    }


    /**
     * @param pField Kopiertes Spielfeld vor nächstem Zug (damit nur Value und nicht Refernce übergeben wird)
     * @param pDirection Richtung des nächsten Zuges, der hier ausgeführt werden soll
     * @return (Maximale) Anzahl der freien Felder
     */
    @Override
    public int getScore(Tile[][] pField, Directions pDirection)
    {
      //  System.out.println("In der Methode getScore");
        Tile[][] newField;
        aTileCreator.generateField(pField.length);
        newField = aGameController.calculateNewField(pDirection, Utilities.copyField(pField));
        newField = aTileCreator.generateNewNumber(newField, 2, 4);
        while(!aGameController.isGameOver(newField))
        {
         //   System.out.println("Im While");
            newField = aGameController.calculateNewField(makeRandomMove(), newField);
            newField = aTileCreator.generateNewNumber(newField, 2, 4);
        }
      //  System.out.println("Ausm While");
        return aGameController.getScore();
    }

    private Directions makeRandomMove() {
        Directions direction;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        switch (randomNum)
        {
            case 1: direction = Directions.UP; break;
            case 2: direction = Directions.DOWN; break;
            case 3: direction = Directions.LEFT; break;
            case 4: direction = Directions.RIGHT; break;
            default: System.out.println("Fehler beim Random -> standardmäßig UP genommen"); direction = Directions.UP;
        }
        return direction;
    }

}
