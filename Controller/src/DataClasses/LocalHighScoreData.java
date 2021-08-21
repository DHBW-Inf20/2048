package DataClasses;

import java.io.Serializable;
import java.util.Date;

public class LocalHighScoreData implements Serializable
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

    public LocalHighScoreData(Date date, int score)
    {
        this.date = date;
        this.score = score;
    }

    private int score;
}

