package AI;

import DataClasses.Directions;
import DataClasses.GameOptions;
import DataClasses.Tile;

public interface IAIPlayer
{
    Directions calculateNextDirection(Tile[][] field);
    int getScore(Tile[][] field, Directions directions);
    Directions makeRandomMove();
}
