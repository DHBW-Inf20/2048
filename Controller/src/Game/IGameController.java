package Game;

import Game.DataClasses.Directions;
import Game.DataClasses.GameModes;
import Game.Listeners.ScoreChangeListener;
import Game.Listeners.TileChangeListener;

public interface IGameController
{
    void startGame(GameModes mode, boolean aiEnabled, int dimension);

    void makeMove(Directions direction);

     void setTileChangeListener(TileChangeListener tileChangeListener);

     void setScoreChangeListener(ScoreChangeListener scoreChangeListener);

     void setTileSize(double tileSize, int tileCount);

}
