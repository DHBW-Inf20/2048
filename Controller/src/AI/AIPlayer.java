package AI;

import DataClasses.Directions;
import DataClasses.GameOptions;
import DataClasses.Tile;
import Game.GameController;
import Game.IGameController;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.valueOf;

public class AIPlayer implements IAIPlayer
{
    private IGameController aGameController;
    private int aRounds = 5;

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
        int up, down, left, right;

        for(int i = 0; i < aRounds; i++)
        {
            System.out.println("For-Loop erreicht");
            up = getScore(pField, Directions.UP);
            down = getScore(pField, Directions.DOWN);
            left = getScore(pField, Directions.LEFT);
            right = getScore(pField, Directions.RIGHT);

            System.out.println("Oben: " + up);
            System.out.println("Unten: " + down);
            System.out.println("Links: " + left);
            System.out.println("Rechts: " + right);

            appearances.put(Directions.UP, appearances.get(Directions.UP)+up);
            appearances.put(Directions.DOWN, appearances.get(Directions.DOWN)+down);
            appearances.put(Directions.LEFT, appearances.get(Directions.LEFT)+left);
            appearances.put(Directions.RIGHT, appearances.get(Directions.RIGHT)+right);
        }
        // Findet die maximale Value und gibt zugehörigen Key (also eine Direction) zurück
        return Collections.max(appearances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    /**
     * @param pField Kopiertes Spielfeld vor nächstem Zug (damit nur Value und nicht Refernce übergeben wird)
     * @param pDirection Richtung des nächsten Zuges, der hier ausgeführt werden soll
     * @return (Maximale) Anzahl der freien Felder
     */
    @Override
    public int getScore(Tile[][] pField, Directions pDirection)
    {
        System.out.println("In der Methode getScore");
        Tile[][] newField;
        newField = aGameController.calculateNewField(pDirection, pField.clone());
        while(!aGameController.isGameOver(newField))
        {
            System.out.println("Im While");
            newField = aGameController.calculateNewField(makeRandomMove(), newField.clone());
        }
        System.out.println("Ausm While");
        return aGameController.getScore();
    }

    private Directions makeRandomMove() {
        Directions direction;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        switch (randomNum)
        {
            case 1: direction = Directions.UP; System.out.println("UP"); break;
            case 2: direction = Directions.DOWN; System.out.println("DOWN"); break;
            case 3: direction = Directions.LEFT; System.out.println("LEFT"); break;
            case 4: direction = Directions.RIGHT; System.out.println("RIGHT"); break;
            default: System.out.println("Fehler beim Random -> standardmäßig UP genommen"); direction = Directions.UP;
        }
        return direction;
    }

}
