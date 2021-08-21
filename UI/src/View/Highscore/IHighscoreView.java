package View.Highscore;

import javafx.event.Event;
import javafx.scene.Scene;

import java.io.IOException;

public interface IHighscoreView
{
    void createSceneHighscore(Event event, Scene scene) throws IOException;
}
