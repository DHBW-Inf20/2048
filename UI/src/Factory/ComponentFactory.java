package Factory;

import AI.AIPlayer;
import AI.IAIPlayer;
import DataClasses.PlayerData;
import Game.GameController;
import Game.IGameController;
import HighScore.LocalHighScoreController;
import HighScore.ILocalHighScoreController;
import PlayerData.PlayerDataManager;
import PlayerData.IPlayerDataManager;

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
    public static ILocalHighScoreController getHighScroeController()
    {
        return new LocalHighScoreController();
    }
    public static IAIPlayer getAIPlayer()
    {
        return new AIPlayer();
    }

    public static IPlayerDataManager getPlayerDataManager()
    {
        return new PlayerDataManager();
    }
}
