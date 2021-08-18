package HighScore;

import Game.DataClasses.Tile;

public interface IHighScoreController
{
    /**
     * @param newScore Neue Punktzahl (wird intern geprüft ob es Highscore wird)
     * @param dimension Ausgewählte Dimension z.B. 4x4
     */
    void submitNewScore(int newScore, int dimension);
    
    /**
     * @param dimension Ausgewählte Dimension z.B. 4x4
     * @return HighScore-Daten
     */
    HighScoreData getCurrentHighScoreData(int dimension);
}


