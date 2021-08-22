package HighScore;

import DataClasses.GameOptions;
import DataClasses.GlobalHighScoreData;
import DataClasses.LocalHighScoreData;

import java.util.List;

public interface IGlobalHighscoreController
{
    /**
     * Übermittelt einen Highscore an den Backend Server
     * @param score Der zu übermittelnde Score
     * @param options Die Gameoptions
     */
    void submitHighscore(int score, GameOptions options);

    /**
     * @return Gibt eine Globale Highscoreliste vom Backendserver zurück
     */
    List<GlobalHighScoreData> getGlobalHighscores();
}
