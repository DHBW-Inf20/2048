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
    private int aRounds = 250;
    private ITileCreator aTileCreator;
    private GameModes aGameMode;

    /**
     * @param pGameOptions Übergabe von Spielmodus, Wiederholungen/Move und weiteren relevanten Optionen
     */
    public AIPlayer(GameOptions pGameOptions)
    {
        this.aGameController = new GameController();
        this.aGameController.startGame(pGameOptions);
        aGameMode = pGameOptions.getGameMode();
        aRounds = pGameOptions.getAiRepetitions();

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
     * @param pField Aktuelles Feld (wie es auch in der GUi angezeigt wird)
     * @return Direction, welche vom Game Controller und der UI ausgeführt wird
     */
    @Override
    public Directions calculateNextDirection(Tile[][] pField)
    {

        Map<Directions,Long> appearances = new HashMap<>();
        appearances.put(Directions.UP, 0L);
        appearances.put(Directions.DOWN,0L);
        appearances.put(Directions.LEFT,0L);
        appearances.put(Directions.RIGHT,0L);
        int up, down, left, right;

        for(int i = 0; i < aRounds; i++)
        {
            up = getScore(pField, Directions.UP);
            down = getScore(pField, Directions.DOWN);
            left = getScore(pField, Directions.LEFT);
            right = getScore(pField, Directions.RIGHT);

            appearances.put(Directions.UP, appearances.get(Directions.UP)+Long.valueOf(up));
            appearances.put(Directions.DOWN, appearances.get(Directions.DOWN)+Long.valueOf(down));
            appearances.put(Directions.LEFT, appearances.get(Directions.LEFT)+Long.valueOf(left));
            appearances.put(Directions.RIGHT, appearances.get(Directions.RIGHT)+Long.valueOf(right));
        }
        // Findet die maximale Value und gibt zugehörigen Key (also eine Direction) zurück
        var direction =  Collections.max(appearances.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(direction);
        return direction;
    }


    /**
     * @param pField Aktuelles Feld (wie es auch in der UI ist) wird übergeben
     * @param pDirection Eine der vier Richtungen wird zur Simulation übergeben
     * Spielfeld wird kopiert und auf diesem kopierten Spielfeld dann simuliert
     * Erster Move durch "pDirection" vorgegeben, danach random Moves bis zum Game Over
     * @return Score, welcher beim Game Over vorhanden war
     */
    @Override
    public int getScore(Tile[][] pField, Directions pDirection)
    {
        Tile[][] newField;
        aTileCreator.generateField(pField.length);
        newField = aGameController.calculateNewField(pDirection, Utilities.copyField(pField));
        newField = aTileCreator.generateNewNumber(newField, 2, 4);
        while(!aGameController.isGameOver(newField))
        {
            newField = aGameController.calculateNewField(makeRandomMove(), newField);
            newField = aTileCreator.generateNewNumber(newField, 2, 4);
        }
        return aGameController.getScore();
    }

    /**
     * @return Direction
     * Erzeugt eine Random zahl zwischen 1 und 4 und gibt dementpsrechend eine Richtung zurück
     */
    public Directions makeRandomMove() {
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
