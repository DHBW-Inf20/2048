package View.Game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class GameView implements IGameView
{

    //Binding Variablen
    @FXML
    private Label myLabel;

    //Globale Variablen
    private int boardSize;


    public GameView() throws IOException {


    }


    /**
     * Setzt die größe des Spielfelds -> wird von MenueViewModel gesetzt
     *
     * @param boardSize
     */
    public void setTileCount(int boardSize){
        this.boardSize = boardSize;
        myLabel.setText("Size: " + boardSize);
    }
}
