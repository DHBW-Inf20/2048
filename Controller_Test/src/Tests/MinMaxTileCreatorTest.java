package Tests;

import Game.DataClasses.Tile;
import Game.TileCreator.MinMaxTileCreator;
import org.junit.Test;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import Game.DataClasses.Tile;
import javafx.geometry.Point2D;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MinMaxTileCreatorTest
{
    private int dimensions = 3;

    @Test
    public void TestPlaceTile()
    {
        boolean check = false;

        /*Tile[][] field = {
                {create3x3Tile(2,0,0), null, create3x3Tile(2,0,2), null},
                {null,create3x3Tile(4,1,1),  null},
                {null,null,  create3x3Tile(2,2,2)},
                {null, null, null, null}
        };*/

        Tile[][] field = {
                {create3x3Tile(2,0,0), null, null, null},
                {null,null, null,  null},
                {null,null,  null, null},
                {null, null, null, null}
        };


        MinMaxTileCreator minMaxTileCreator = new MinMaxTileCreator();
        int tilesBefore = countFreeTiles(field);
        var newField = minMaxTileCreator.generateNewNumber(field,2,9);
        int tilesAfter = countFreeTiles(field);
        if(tilesBefore == tilesAfter-1){
            check=true;
        }
        assertTrue(check);
    }





    private Tile create3x3Tile(int number, int x, int y)
    {
        return new Tile(number,null,null,x,y,2,9);
    }

    private List<Point2D> generatePossibleTiles(Tile[][] field){ //generiert die möglichen Felder, auf denen platziert werden kann.
        List<Point2D> freeTiles = new ArrayList<Point2D>();
        for(int i=0; i<dimensions; i++){
            for(int j=0; j< dimensions; j++){
                if(field[i][j] == null){
                    freeTiles.add(new Point2D(i,j));
                }

            }
        }

        return freeTiles;
    }

    private int countFreeTiles(Tile[][] field){ //Score des MAX-Spielers
        List<Point2D> freeTiles = generatePossibleTiles(field);
        return freeTiles.size();
    }
}
