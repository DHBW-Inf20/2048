package View.ModusMenue;

import javafx.event.Event;

import java.io.IOException;

public interface IModusMenueView {

    void createModusMenueScene(Event event) throws IOException;

    void setKiMode(boolean kiMode);

    void setTileCount(int tileCount);

    void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight);
}
