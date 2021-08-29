package View.ModusMenue;

import javafx.event.Event;
import javafx.scene.Scene;

import java.io.IOException;

public interface IModusMenueView {

    void createModusMenueScene(Event event, Scene scene) throws IOException;

    void setKiMode(boolean kiMode);

    void setTileCount(int tileCount);

    void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight);

    void setGamemodeMinMax();

    void setGamemodeCooperative();

    void setGamemodeRandom();

    void setGamemodeMinMaxCooperative();

}
