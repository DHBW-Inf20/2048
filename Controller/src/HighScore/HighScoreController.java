package HighScore;

import Game.DataClasses.HighScoreData;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

public class HighScoreController implements IHighScoreController
{
    private HashMap<Integer, HighScoreData> currentHighScoreData;

    public HighScoreData getCurrentHighScoreData(int dimension)
    {
        if(!currentHighScoreData.containsKey(dimension)) return new HighScoreData(new Date(),0);
        return currentHighScoreData.get(dimension);
    }

    private final String highscoreDataLocation = "./highscore.dat";

    public HighScoreController()
    {
        try
        {
           readHighScoreDataFromFile();
        } catch (IOException | ClassNotFoundException e)
        {
            this.currentHighScoreData = new HashMap<>();
        }
    }

    private void readHighScoreDataFromFile() throws IOException, ClassNotFoundException
    {
        File f = new File(highscoreDataLocation);
        if(!f.exists()) throw new FileNotFoundException();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(highscoreDataLocation));
        currentHighScoreData = (HashMap<Integer,HighScoreData>) in.readObject();
    }

    private void writeHighScoreDataToFile() throws IOException
    {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(highscoreDataLocation));
        os.writeObject(currentHighScoreData);
    }

    @Override
    public void submitNewScore(int newScore, int dimension)
    {
            if(!currentHighScoreData.containsKey(dimension) || currentHighScoreData.get(dimension).getScore()<newScore)
            {
                currentHighScoreData.put(dimension,new HighScoreData(new Date(), newScore));
                try
                {
                    writeHighScoreDataToFile();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    }
}

