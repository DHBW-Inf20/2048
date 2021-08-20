package Game.DataClasses;

import java.io.Serializable;

public class GameOptions implements Serializable
{
    public GameOptions(boolean aiEnabled, int fieldDimensions, GameModes gameMode)
    {
        this.aiEnabled = aiEnabled;
        this.fieldDimensions = fieldDimensions;
        this.tileCreator = gameMode;
    }

    private boolean aiEnabled = false;
    private int fieldDimensions = 4;
    private GameModes tileCreator = GameModes.random;

    public boolean isAiEnabled()
    {
        return aiEnabled;
    }


    public int getFieldDimensions()
    {
        return fieldDimensions;
    }



    public GameModes getGameMode()
    {
        return tileCreator;
    }




    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameOptions that = (GameOptions) o;

        if (aiEnabled != that.aiEnabled) return false;
        if (fieldDimensions != that.fieldDimensions) return false;
        return tileCreator == that.tileCreator;
    }

    /**
     * Muss überschrieben werden, das sonst Gameoptions nicht als Key in der Hashmap zur Speicherung der Highscores verwendet werden kann
     * @return Eindeutiger Hashcode der Gameoptions
     */
    @Override
    public int hashCode()
    {
        int result = (aiEnabled ? 1 : 0);
        result = 31 * result + fieldDimensions;
        result = 31 * result + (tileCreator != null ? tileCreator.hashCode() : 0);
        return result;
    }
}
