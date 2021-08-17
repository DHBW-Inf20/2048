package HighScore;

import java.io.*;
import java.security.Timestamp;
import java.util.Date;

public class HighScoreController implements IHighScoreController
{
    private HighScoreData currentHighScoreData;

    public HighScoreData getCurrentHighScoreData()
    {
        return currentHighScoreData;
    }

    private final String highscoreDataLocation = "./highscore.txt";

    public HighScoreController()
    {
        try
        {
            this.currentHighScoreData = readHighScoreData();
        } catch (IOException | ClassNotFoundException e)
        {
            this.currentHighScoreData = new HighScoreData(new Date(),0);
        }
    }

    private HighScoreData readHighScoreData() throws IOException, ClassNotFoundException
    {
        File f = new File(highscoreDataLocation);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(highscoreDataLocation));
        return (HighScoreData) in.readObject();

    }

    private void writeHighScoreData(HighScoreData highScoreData) throws IOException
    {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(highscoreDataLocation));
        os.writeObject(highScoreData);
    }

    @Override
    public void submitNewScore(int newScore)
    {
            if(currentHighScoreData.getScore()<newScore)
            {
                currentHighScoreData = new HighScoreData(new Date(), newScore);
                try
                {
                    writeHighScoreData(currentHighScoreData);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    }
}

