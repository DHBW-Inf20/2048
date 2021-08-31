package Factory;

import AI.AIPlayer;
import AI.IAIPlayer;
import DataClasses.GameOptions;
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

    public static IHighscoreController getHighScoreController()
    {
        return new HighscoreController(getPlayerDataManager());
    }

    public static IAIPlayer getAIPlayer(GameOptions gameOptions)
    {
        return new AIPlayer(gameOptions);
    }

    public static IPlayerDataController getPlayerDataManager()
    {
        return new PlayerDataController();
    }
}
