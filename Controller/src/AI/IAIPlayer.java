package AI;

import DataClasses.Directions;
import DataClasses.Tile;

public interface IAIPlayer
{
    Directions calculateNextDirection(Tile[][] field);
    int getScore(Tile[][] field, Directions directions, int depth);
    int getFreeTiles(Tile[][] field);
}
