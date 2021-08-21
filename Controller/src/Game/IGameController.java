package Game;

import DataClasses.Directions;
import DataClasses.GameOptions;
import Game.Listeners.ScoreChangeListener;
import Game.Listeners.TileChangeListener;

public interface IGameController
{
    void startGame(GameOptions gameOptions);

    void makeMove(Directions direction);

     void setTileChangeListener(TileChangeListener tileChangeListener);

     void setScoreChangeListener(ScoreChangeListener scoreChangeListener);

     void setTileSize(double tileSize, int tileCount);

     int getGameStatus();
}
