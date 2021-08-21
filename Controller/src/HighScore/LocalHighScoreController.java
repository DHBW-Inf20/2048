package HighScore;

import DataClasses.GameOptions;
import DataClasses.HighScoreData;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

public class LocalHighScoreController implements ILocalHighScoreController
{
    private HashMap<GameOptions, HighScoreData> currentHighScoreData;

    private final String highscoreDataLocation = "./highscore.dat";
    private GameOptions gameOptions;

    public HighScoreData getCurrentHighScoreData()
    {
        if(!currentHighScoreData.containsKey(gameOptions)) return new HighScoreData(new Date(),0);
        return currentHighScoreData.get(gameOptions);
    }


    public LocalHighScoreController()
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
        currentHighScoreData = (HashMap<GameOptions,HighScoreData>) in.readObject();
    }

    private void writeHighScoreDataToFile() throws IOException
    {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(highscoreDataLocation));
        os.writeObject(currentHighScoreData);
    }

    @Override
    public void submitNewScore(int newScore)
    {
            if(!currentHighScoreData.containsKey(gameOptions) || currentHighScoreData.get(gameOptions).getScore()<newScore)
            {
                currentHighScoreData.put(gameOptions,new HighScoreData(new Date(), newScore));
                try
                {
                    writeHighScoreDataToFile();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void setGameOptions(GameOptions gameOptions)
    {
        this.gameOptions = gameOptions;
    }
}

