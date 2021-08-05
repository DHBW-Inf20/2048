package View.Credits;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;

import java.io.IOException;

public interface ICreditsView {

    void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight);

    void createSceneCredits(Event event, Scene scene) throws IOException;
}
