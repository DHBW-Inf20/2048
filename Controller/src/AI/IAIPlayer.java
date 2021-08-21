package AI;

import DataClasses.Directions;
import DataClasses.Tile;

public interface IAIPlayer
{
    Directions calculateNextDirection(Tile[][] field);
}
