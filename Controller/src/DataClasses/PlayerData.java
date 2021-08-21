package DataClasses;

import java.io.Serializable;

public class PlayerData implements Serializable
{
    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    private String playerName = "Unnamed";
}
