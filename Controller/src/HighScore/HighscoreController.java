package HighScore;

import DataClasses.GameModes;
import DataClasses.GameOptions;
import DataClasses.GlobalHighScoreData;
import DataClasses.LocalHighScoreData;
import PlayerData.IPlayerDataController;

import java.util.List;

public class HighscoreController implements IHighscoreController
{
    private GameOptions gameOptions;
    private final ILocalHighScoreController localHighScoreController = new LocalHighscoreController();
    private final IGlobalHighscoreController globalHighscoreController;

    public HighscoreController(IPlayerDataController iPlayerDataManager)
    {
         globalHighscoreController= new GlobalHighscoreController(iPlayerDataManager);
    }

    /**
     * @param newScore Neuer Score (wird intern auf Highscore überprüft)
     */
    @Override
    public void submitNewScore(int newScore)
    {
        if(localHighScoreController.submitNewScore(newScore)) //Wenn der neue Score ein Highscore ist
        {
            if(!gameOptions.isAiEnabled()&&gameOptions.getGameMode()== GameModes.random) //Globale Highscores werden nur für den Standardgamemode Random-Tile-Creator ohne KI verwendet
            {
                new Thread(() ->
                {
                    globalHighscoreController.submitHighscore(newScore, gameOptions);
                }).start();
            }
        }
    }

    @Override
    public List<GlobalHighScoreData> getGlobalHighscore()
    {
        return globalHighscoreController.getGlobalHighscores();
    }

    /**
     * @param gameOptions Spieloptionen - Wichtig, da verschiedene Spielmodis, Feldgrößen und KI an/aus
     */
    @Override
    public void setGameOptions(GameOptions gameOptions)
    {
        this.gameOptions = gameOptions;
        localHighScoreController.setGameOptions(gameOptions);
    }

    @Override
    public LocalHighScoreData getCurrentHighScoreData()
    {
        return localHighScoreController.getCurrentHighScoreData();
    }
}
