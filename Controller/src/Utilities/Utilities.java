package Utilities;

import DataClasses.Tile;

public class Utilities
{
    public static Tile[][] copyField(Tile[][] in)
    {
        Tile[][] array = new Tile[in.length][in.length];
        for (int j = 0; j < in.length; j++) {
            for (int k = 0; k < in[j].length; k++) {
                array[j][k] = in[j][k];
            }
        }
        return array;
    }
}
