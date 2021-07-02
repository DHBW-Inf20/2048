package Game.TileCreator;

import Game.DataClasses.Tile;

public interface ITileCreator
{
    Tile[][] generateField(int dimensions);
}
