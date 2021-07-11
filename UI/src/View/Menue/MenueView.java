package View.Menue;

import Game.DataClasses.GameModes;
import Game.GameController;
import Game.IGameController;
import View.ModusMenue.ModusMenueView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenueView extends Application implements IMenueView, Initializable {

    //Binding variables
    @FXML
    private Slider sliderSize;
    @FXML
    private Button buttonNewGame;
    @FXML
    private ToggleButton toggleButton;

    //Globale Variablen
    private int tileCount;
    private boolean kiMode = false;

    //Einstellbare Konstanten für die Fenstergröße
    private final int minWindowWidth = 500;
    private final int minWindowHeight = 700;
    private final int windowWidth = 600;
    private final int windowHeight = 800;


    /**
     * Konsruktor
     *
     */
    public MenueView() {

    }

    /**
     * Wird beim öffnen der Applikation ausgeführt
     * Initalisiert die erste Szene (das Menü)
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Erzeuge eine Szene aus MenueView.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/View/Menue/MenueView.fxml"));

        //Füge Titel und Icon hinzu
        primaryStage.setTitle("2048");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Styles/icon.png")));

        Scene menueScene = new Scene(root, windowWidth, windowHeight);
        primaryStage.setScene(menueScene);

        primaryStage.setMinWidth(minWindowWidth);
        primaryStage.setMinHeight(minWindowHeight);
        //primaryStage.setResizable(false);


        primaryStage.show();
    }

    /**
     * Ist die initiale Funktion
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Initialisiert die interaktive Elemente (Slider)
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialisiere das Label
        tileCount = (int) sliderSize.getValue();
        buttonNewGame.setText("New " + tileCount + "x" + tileCount);

        //Anonymer ActionListener für den Slider -> stetzt das Label auf den Wert
        sliderSize.valueProperty().addListener((observable, oldValue, newValue) -> {

            tileCount = (int) sliderSize.getValue();
            buttonNewGame.setText("New " + tileCount + "x" + tileCount);
        });

    }


    /**
     * Wird beim klicken auf "New Game" Ausgeführt -> erzeugt das nächste Fenster
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressNewGame(ActionEvent event) throws IOException {


        //Erzeugt neue Instanz des Controlers und ruft Methode zum Erzeugen des Fesnters auf
        ModusMenueView modusMenue = new ModusMenueView();
        modusMenue.setWindowDimensions(windowWidth, windowHeight, minWindowWidth, minWindowHeight);
        modusMenue.setTileCount(tileCount);
        modusMenue.setKiMode(kiMode);
        modusMenue.createModusMenueScene(event);
    }

    /**
     * Wird durch den toggle button ausgeführt
     */
    public void switchKiMode() {
        kiMode = !kiMode;

        if (kiMode) {
            toggleButton.setText("KI: On");
        } else {
            toggleButton.setText("KI: Off");
        }
    }


    /**
     * Wird beim Drücken auf "Credits" ausgeführt
     *
     * @param event
     */
    public void onButtonPressCredits(ActionEvent event) throws IOException {

        //TODO: ff Auslagern -> siehe TODO in CreditsView

        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Credits/CreditsView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
