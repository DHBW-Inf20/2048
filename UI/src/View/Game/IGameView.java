package View.Game;

import Game.DataClasses.GameModes;
import Game.DataClasses.Tile;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;

import java.io.IOException;

public interface IGameView {

    void createGameScene(Event event, Scene scene) throws IOException;

    void setKiMode(boolean kiMode);

    void setTileCount(int tileCount);

    void setHighscore(int highscore);

    void setScoreLabel(int score);

    void setGameMode(GameModes gameMode);
}
