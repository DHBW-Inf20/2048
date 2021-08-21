package Factory;

import AI.AIPlayer;
import AI.IAIPlayer;
import Game.GameController;
import Game.IGameController;
import HighScore.HighscoreController;
import HighScore.IHighscoreController;
import HighScore.LocalHighscoreController;
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
    public static IHighscoreController getHighScroeController()
    {
        return new HighscoreController(getPlayerDataManager());
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
