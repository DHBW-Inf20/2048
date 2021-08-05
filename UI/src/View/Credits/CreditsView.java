package View.Credits;

import View.Menue.MenueView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditsView implements ICreditsView {

    private int windowWidth;
    private int windowHeight;
    private int minWindowWidth;
    private int minWindowHeight;

    public CreditsView() {


    }

    /**
     * Erstellt die Senen -> wird vom Menü aus aufgerufen
     *
     * @param event Event
     * @param scene Scene
     * @throws IOException
     */
    public void createSceneCredits(Event event, Scene scene) throws IOException {

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wird beim Drücken des Buttons "Back" ausgeführt -> Öffnet die Menü Scene
     * *
     * @param event Event
     * @throws IOException
     */
    public void onButtonPressBack(ActionEvent event) throws IOException {

        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menue/MenueView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Übergibt die Parameter der Fenstergröße
     *
     * @param windowWidth Fensterhöhe
     * @param windowHeight  Fensterbreite
     * @param minWindowWidth Maximale Fensterhöhe
     * @param minWindowHeight Maximale Fensterbreite
     */
    @Override
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight) {

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = minWindowHeight;
    }
}
