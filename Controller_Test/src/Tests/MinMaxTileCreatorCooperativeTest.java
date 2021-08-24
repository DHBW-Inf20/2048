import DataClasses.Tile;
import Game.TileCreator.MinMaxTileCreatorCooperative;
import javafx.geometry.Point2D;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MinMaxTileCreatorCooperativeTest
{
    private int dimensions = 4;

    /**
     * Testet das Platzieren von neuen Tiles mit dem MINMAXTILEGENERATOR
     */
    @Test
    public void TestPlaceTile()
    {
        boolean check = false;

        MinMaxTileCreatorCooperative minMaxTileCreator = new MinMaxTileCreatorCooperative();
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


    /**
     * generiert ein Tile
     * @param number Zahl des Tile
     * @param x x-Koordinate des Tile
     * @param y y-Koordinate des Tile
     * @return Tile
     */
    private Tile create3x3Tile(int number, int x, int y)
    {
        return new Tile(number,null,null,x,y,2,9);
    }

    /**
     * generiert die möglichen Felder, auf denen platziert werden kann.
     * @param field Spielfeld
     * @return liste der möglichen Felder
     */
    private List<Point2D> generatePossibleTiles(Tile[][] field){ //
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

    /**
     * Zählt freie Felder auf dem Spielfeld
     * @param field Spielfeld
     * @return Anzahl freie Felder
     */
    private int countFreeTiles(Tile[][] field){ //Score des MAX-Spielers
        List<Point2D> freeTiles = generatePossibleTiles(field);
        return freeTiles.size();
    }
}
