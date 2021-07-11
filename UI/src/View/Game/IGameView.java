package View.Game;

import Game.DataClasses.Tile;
import javafx.event.ActionEvent;

import java.io.IOException;

public interface IGameView {

    void createGameScene(ActionEvent event) throws IOException;

    void setKiMode(boolean kiMode);

    void setTileCount(int tileCount);

    void setHighscore(int highscore);

    void setScore(int score);

}
