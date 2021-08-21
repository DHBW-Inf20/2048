package DataClasses;

public class GlobalHighScoreData
{
    private String name;

    public GlobalHighScoreData(String name, int score)
    {
        this.name = name;
        this.score = score;
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
