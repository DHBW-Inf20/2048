package DataClasses;

public class GlobalHighScoreData
{
    private String name;

    public int getDimensions()
    {
        return dimensions;
    }

    private int dimensions;

    public GlobalHighScoreData(String name, int score, int dimensions)
    {
        this.name = name;
        this.score = score;
        this.dimensions = dimensions;
    }

    public String getName()
    {
        return name;
    }

    public int getScore()
    {
        return score;
    }

    private int score;

}
