package Game.DataClasses;

import java.io.Serializable;
import java.util.Date;

public class HighScoreData implements Serializable
{
    public Date getDate()
    {
        return date;
    }

    public int getScore()
    {
        return score;
    }

    private Date date;

    public HighScoreData(Date date, int score)
    {
        this.date = date;
        this.score = score;
    }

    private int score;
}
