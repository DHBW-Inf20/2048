package HighScore;

import DataClasses.GameOptions;
import DataClasses.LocalHighScoreData;

import java.io.*;
import java.util.Date;
import java.util.HashMap;


public class LocalHighscoreController implements ILocalHighScoreController
{
    private HashMap<GameOptions, LocalHighScoreData> currentHighScoreData;

    private final String highscoreDataLocation = "./highscore.dat";
    private GameOptions gameOptions;

    public LocalHighScoreData getCurrentHighScoreData()
    {
        if(!currentHighScoreData.containsKey(gameOptions)) return new LocalHighScoreData(new Date(),0);
        return currentHighScoreData.get(gameOptions);
    }


    public LocalHighscoreController()
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
        currentHighScoreData = (HashMap<GameOptions, LocalHighScoreData>) in.readObject();
    }

    private void writeHighScoreDataToFile() throws IOException
    {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(highscoreDataLocation));
        os.writeObject(currentHighScoreData);
    }

    @Override
    public boolean submitNewScore(int newScore)
    {
            if(!currentHighScoreData.containsKey(gameOptions) || currentHighScoreData.get(gameOptions).getScore()<newScore)
            {
                currentHighScoreData.put(gameOptions,new LocalHighScoreData(new Date(), newScore));
                try
                {
                    writeHighScoreDataToFile();
                    return true;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return false;
    }

    @Override
    public void setGameOptions(GameOptions gameOptions)
    {
        this.gameOptions = gameOptions;
    }
}

