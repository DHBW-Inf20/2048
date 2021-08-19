package Factory;

import AI.AIPlayer;
import AI.IAIPlayer;
import Game.GameController;
import Game.IGameController;
import HighScore.HighScoreController;
import HighScore.IHighScoreController;

/*
Hier werden alle Abhängigkeiten zu Implementierungen aus dem Controller-Module durch das Factory-Pattern und Interfaces entkoppelt
-> Mapping von Interface und Implementierung
 */
public class ComponentFactory
{
    public static IGameController getGameController()
    {
        return new GameController();
    }
    public static IHighScoreController getHighScroeController()
    {
        return new HighScoreController();
    }
    public static IAIPlayer getAIPlayer()
    {
        return new AIPlayer();
    }
}
