package Tests;

import DataClasses.Tile;
import Game.TileCreator.MinMaxTileCreator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import javafx.geometry.Point2D;



import static org.junit.Assert.assertTrue;

public class MinMaxTileCreatorTest
{
    private int dimensions = 4;

    @Test
    public void TestPlaceTile()
    {
        boolean check = false;

        MinMaxTileCreator minMaxTileCreator = new MinMaxTileCreator();
        Tile[][] field = minMaxTileCreator.generateField(4);
        int tilesBefore = countFreeTiles(field);
        var newField = minMaxTileCreator.generateNewNumber(field,2,9);
        int tilesAfter = countFreeTiles(newField);
        if(tilesBefore == tilesAfter+1){
            check=true;
        }
        while (countFreeTiles(newField)>0){
            tilesBefore = countFreeTiles(newField);
            newField = minMaxTileCreator.generateNewNumber(newField, 2, 9);
            tilesAfter = countFreeTiles(newField);
            if(tilesBefore == tilesAfter+1){
                check=true;
            } else {
                check=false;
                assertTrue(check);
            }
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
