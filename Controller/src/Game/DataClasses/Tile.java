package Game.DataClasses;

public class Tile
{

    private int number;
    private Tile preFieldA;
    private Tile preFieldB;


    private int posX;
    private int posY;


    public Tile(int number, Tile preFieldA, Tile preFieldB, int posX, int posY)
    {
        this.number = number;
        this.preFieldA = preFieldA;
        this.preFieldB = preFieldB;
        this.posX = posX;
        this.posY = posY;

        if(preFieldA!=null)preFieldA.clearPreTiles();
        if(preFieldB!=null)preFieldB.clearPreTiles();
    }


    public void clearPreTiles()
    {
        this.preFieldA = null;
        this.preFieldB = null;
    }

    public int getNumber()
    {
        return number;
    }

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }


}
