package HighScore;

import Game.DataClasses.Tile;

public interface IHighScoreController
{
    void submitNewScore(int newScore);
    HighScoreData getCurrentHighScoreData();
}


