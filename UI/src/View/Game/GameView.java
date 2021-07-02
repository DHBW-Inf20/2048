package View.Game;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class GameView implements IGameView {

    //Binding Variablen
    @FXML
    private Label myLabel;

    //Globale Variablen
    private int boardSize;
    private boolean kiMode;


    public GameView() throws IOException {


    }


    /**
     * Setzte die Spielfeldgröße.
     * Wird vom Menü aus durchgeschleift.
     *
     * @param boardSize
     */
    @Override
    public void setTileCount(int boardSize) {
        this.boardSize = boardSize;
        myLabel.setText("Size: " + boardSize);
    }


    /**
     * Setzt den Modus für die Ki auf Wahr oder Falsch.
     * Wir so vom Menü übergeben.
     *
     * @param kiMode
     */
    @Override
    public void setKiMode(boolean kiMode) {
        this.kiMode = kiMode;
    }

}
