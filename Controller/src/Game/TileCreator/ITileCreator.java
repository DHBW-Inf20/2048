package Game.TileCreator;

import DataClasses.Tile;

public interface ITileCreator
{
    /**
     * @param dimensions Dimensionen des Spielfeld
     * @return Leeres Spielfed
     */
    Tile[][] generateField(int dimensions);


    /**
     * @param tiles Bisheriges Spielfeld
     * @param tileSize Größe der Spielsteine
     * @param tileCount Anzahl der Spielsteine
     * @return neues Spielfeld mit neu generierten Spielsten
     */
    Tile[][] generateNewNumber(Tile[][] tiles, double tileSize, int tileCount);
    
}
