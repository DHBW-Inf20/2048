package View.Highscore;

import DataClasses.GlobalHighScoreData;
import Factory.ComponentFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    private int windowWidth;
    private int windowHeight;
    private int minWindowWidth;
    private int minWindowHeight;


    public void createSceneHighscore(Event event, Scene scene) throws IOException {
        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        
        StringBuilder sb = new StringBuilder();
        List<GlobalHighScoreData> highscores = ComponentFactory.getHighScoreController().getGlobalHighscore();
        Collections.reverse(highscores);
        highscores.forEach(globalHighScoreData -> {
            sb.append(globalHighScoreData.getName()).append(": ").append(globalHighScoreData.getScore()).append(" (").append(globalHighScoreData.getDimensions()).append("x").append(globalHighScoreData.getDimensions()).append(")").append("\n");
        });
        highscoreList.setText(sb.toString());
    }

    @Override
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight)
    {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = minWindowHeight;
    }

    public void onButtonPressBack(ActionEvent event) throws IOException
    {
        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menue/MenueView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

