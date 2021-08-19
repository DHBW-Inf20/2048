package AI;

import Game.DataClasses.Directions;
import Game.DataClasses.Tile;

public class AIPlayer implements IAIPlayer
{
    @Override
    public Directions calculateNextDirection(Tile[][] field)
    {
        return Directions.UP;
    }
}
