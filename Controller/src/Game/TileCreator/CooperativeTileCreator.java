package Game.TileCreator;

import Game.DataClasses.Tile;

public class CooperativeTileCreator implements ITileCreator
{
    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }

    @Override
    public Tile[][] generateNewNumber(Tile[][] tiles) {
        return new Tile[0][];
    }
}
