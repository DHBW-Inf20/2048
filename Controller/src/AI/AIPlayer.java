package AI;

import DataClasses.Directions;
import DataClasses.Tile;

public class AIPlayer implements IAIPlayer
{
    @Override
    public Directions calculateNextDirection(Tile[][] field)
    {
        return Directions.UP;
    }
}
