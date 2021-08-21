package HighScore;

import DataClasses.GlobalHighScoreData;
import DataClasses.LocalHighScoreData;

import java.util.List;

public interface IGlobalHighscoreController
{
    void submitHighscore(int score);
    List<GlobalHighScoreData> getGlobalHighscores();
}
