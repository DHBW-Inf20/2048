package View.Highscore;

import DataClasses.GlobalHighScoreData;
import Factory.ComponentFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class HighscoreView implements IHighscoreView
{


    @FXML
    private TextArea highscoreList;


    public void createSceneHighscore(Event event, Scene scene) throws IOException {
        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        
        StringBuilder sb = new StringBuilder();
        List<GlobalHighScoreData> highscores = ComponentFactory.getHighScroeController().getGlobalHighscore();
        Collections.reverse(highscores);
        highscores.forEach(globalHighScoreData -> {
            sb.append(globalHighScoreData.getName()).append(": ").append(globalHighScoreData.getScore()).append("\n");
        });
        highscoreList.setText(sb.toString());
    }
}

