package HighScore;

import DataClasses.GameOptions;
import DataClasses.GlobalHighScoreData;
import DataClasses.LocalHighScoreData;

import java.util.List;

public interface IHighscoreController
{

    /**
     * @param newScore Neuer Score (wird intern auf Highscore überprüft
     */
    void submitNewScore(int newScore);


    List<GlobalHighScoreData> getGlobalHighscore();

    /**
     * Da für die verschiedenen Spielmodi und Feldgrößen die Highscores getrennt gespeichert werden, müssen die Gameoptions dem HighscoreController bekannt sein
     * @param gameOptions Spieloptionen-
     */
    void setGameOptions(GameOptions gameOptions);

    /**
     * @return HighScore-Daten
     */
    LocalHighScoreData getCurrentHighScoreData();
}
