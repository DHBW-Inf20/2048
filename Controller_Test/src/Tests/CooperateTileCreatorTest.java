package Tests;

import DataClasses.Tile;
import Game.TileCreator.CooperativeTileCreator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CooperateTileCreatorTest
{
    @Test
    public void Test2x2Field()
    {
        Tile[][] field = {
                {create3x3Tile(2,0,0), create3x3Tile(2,0,1), create3x3Tile(2,0,2)},
                {create3x3Tile(4,1,0),create3x3Tile(4,1,1),  create3x3Tile(4,1,2)},
                {null,null,  create3x3Tile(2,2,2)}
        };
        /*
        Feld sieht so aus:
        *  2  2  2
        *  4  2  4
        *  [] [] 2

        Der Cooperate Creator hat folgende Möglichkeiten:

        a) Er die zufällige Spielsteinnummer ist eine 2
        Feld müsste so aussehen:
            *  2  2  2
            *  4  2  4
            *  [] 2  2  <-  Eine 2 bei [2][1]

       b) Er die zufäll ige Spielsteinnummer ist eine 2
          Feld müsste so aussehen:
            *  2  2  2
            *  4  2  4
            *  4  [] 2  <- Eine 4 bei [2][0]
        * */


        CooperativeTileCreator cooperativeTileCreator = new CooperativeTileCreator();
        var newField = cooperativeTileCreator.generateNewNumber(field,2,9);
        boolean check = newField[2][1].getNumber()==2||newField[2][0].getNumber()==4;
        assertTrue(check);
    }
    private Tile create3x3Tile(int number, int x, int y)
    {
        return new Tile(number,null,null,x,y,2,9);
    }
}
