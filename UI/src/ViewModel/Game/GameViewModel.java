package ViewModel.Game;

import ViewModel.Menue.MenueViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameViewModel implements IGameViewModel{

    //Binding Variablen
    @FXML
    private Label myLabel;

    //Globale Variablen
    private int boardSize;


    public GameViewModel() throws IOException {


    }


    /**
     * Setzt die größe des Spielfelds -> wird von MenueViewModel gesetzt
     *
     * @param boardSize
     */
    public void setBoardSize(int boardSize){
        this.boardSize = boardSize;
        myLabel.setText("Size: " + boardSize);
    }
}
