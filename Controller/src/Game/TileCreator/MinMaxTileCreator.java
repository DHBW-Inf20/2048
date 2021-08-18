package Game.TileCreator;

import Game.DataClasses.Tile;

public class MinMaxTileCreator implements ITileCreator
{
    @Override
    public Tile[][] generateField(int dimensions)
    {
        return new Tile[dimensions][dimensions];
    }

    @Override
    public Tile[][] generateNewNumber(Tile[][] field, double tileSize, int tileCount) {
        return new Tile[0][];
    }
}
