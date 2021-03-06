package View.Game;

import DataClasses.GameModes;
import javafx.event.Event;
import javafx.scene.Scene;

import java.io.IOException;

public interface IGameView {

    void createGameScene(Event event, Scene scene) throws IOException;

    void setAiMode(boolean kiMode, int repetitions);

    void setTileCount(int tileCount);

    void setHighscore(int highscore);

    void setScoreLabel(int score);

    void setGameMode(GameModes gameMode);

    void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight);
}
