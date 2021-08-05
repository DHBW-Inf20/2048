package Game;

import Game.DataClasses.Directions;
import Game.DataClasses.GameModes;
import Game.DataClasses.Tile;

public interface IGameController
{
    void startGame(GameModes mode, boolean aiEnabled, int dimension);

    void makeMove(Directions direction);

}
