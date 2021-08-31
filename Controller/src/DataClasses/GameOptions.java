package DataClasses;

import java.io.Serializable;

public class GameOptions implements Serializable
{
    /**
     * @param aiEnabled Ist die KI aktiviert
     * @param fieldDimensions Größes des Feldes
     * @param gameMode Einer der vier Spielmodi
     * @param repetitions Zahl der Wiederholungen, welche die KI pro Zug berechnet
     */
    public GameOptions(boolean aiEnabled, int fieldDimensions, GameModes gameMode, int repetitions)
    {
        this.aiEnabled = aiEnabled;
        this.fieldDimensions = fieldDimensions;
        this.tileCreator = gameMode;
        this.aiRepetitions = repetitions;
    }

    public GameOptions(boolean aiEnabled, int fieldDimensions, GameModes gameMode)
    {
        this.aiEnabled = aiEnabled;
        this.fieldDimensions = fieldDimensions;
        this.tileCreator = gameMode;
    }

    private boolean aiEnabled = false;
    private int fieldDimensions = 4;
    private int aiRepetitions = 250;
    private GameModes tileCreator = GameModes.random;

    public boolean isAiEnabled()
    {
        return aiEnabled;
    }


    public int getFieldDimensions()
    {
        return fieldDimensions;
    }

    public int getAiRepetitions(){ return aiRepetitions; }

    public GameModes getGameMode()
    {
        return tileCreator;
    }


    /**
     * @param o übergebenes Objekt
     * @return prüft auf Gleichheit des Objekte und Gleichheit der Attribute der Objekte
     */
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
