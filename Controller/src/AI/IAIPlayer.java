package AI;

import Game.DataClasses.Directions;
import Game.DataClasses.Tile;

public interface IAIPlayer
{
    Directions calculateNextDirection(Tile[][] field);
}
