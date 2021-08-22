package Factory;

import AI.AIPlayer;
import AI.IAIPlayer;
import Game.GameController;
import Game.IGameController;
import HighScore.HighscoreController;
import HighScore.IHighscoreController;
import PlayerData.PlayerDataController;
import PlayerData.IPlayerDataController;

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
    public static IHighscoreController getHighScroeController()
    {
        return new HighscoreController(getPlayerDataManager());
    }
    public static IAIPlayer getAIPlayer()
    {
        return new AIPlayer();
    }

    public static IPlayerDataController getPlayerDataManager()
    {
        return new PlayerDataController();
    }
}
