package PlayerData;

import DataClasses.PlayerData;

import java.io.*;

public class PlayerDataManager implements IPlayerDataManager
{
    private final String playerDataLocation = "./playerData.dat";

    private PlayerData playerData;


    private void readPlayerDataFromFile() throws IOException, ClassNotFoundException
    {
        File f = new File(playerDataLocation);
        if(!f.exists()) throw new FileNotFoundException();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(playerDataLocation));
        playerData = (PlayerData) in.readObject();
    }

    private void writeHighScoreDataToFile() throws IOException
    {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(playerDataLocation));
        os.writeObject(playerData);
    }

    public PlayerDataManager()
    {
        try
        {
            readPlayerDataFromFile();
        } catch (IOException | ClassNotFoundException e)
        {
            playerData = new PlayerData();
        }
    }

    @Override
    public String getPlayerName()
    {
        return playerData.getPlayerName();
    }

    @Override
    public void setPlayerName(String name)
    {
        this.playerData.setPlayerName(name);
        try
        {
            writeHighScoreDataToFile();
        } catch (IOException e)
        {
            //igonre
        }
    }
}
