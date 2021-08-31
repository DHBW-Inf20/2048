package View.ModusMenue;

import DataClasses.GameModes;
import View.Game.IGameView;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ModusMenueView implements IModusMenueView {

    //Globale Veriablen
    private int minWindowHeight;
    private int minWindowWidth;
    private int windowHeight;
    private int windowWidth;
    private int tileCount;
    private boolean kiMode;
    private GameModes gameMode;
    private IGameView gameView;
    private Slider kiSlider;
    /**
     * Kosntruktor
     */
    public ModusMenueView() {


    }

    /**
     * Erzeugt einen neue Scene für den Controler, wird vom Menü aufgerufen
     *
     * @param event
     * @throws IOException
     */
    @Override
    public void createModusMenueScene(Event event, Scene scene) throws IOException {

        //erstelle eine Togglegruppe für die Radiobuttons
        ToggleButton toggleButtonRamdom = (ToggleButton) scene.lookup("#buttonRandom");
        ToggleButton toggleButtonMinMax = (ToggleButton) scene.lookup("#buttonMinMax");
        ToggleButton toggleButtonMinMaxCooperative = (ToggleButton) scene.lookup("#buttonMinMaxCooperative");
        ToggleButton toggleButtonCorporate = (ToggleButton) scene.lookup("#buttonCorporate");

        if(kiMode) // Slider falls eine KI ausgewählt wurde
        {
            kiSlider = (Slider) scene.lookup("#sliderKi");
            kiSlider.setVisible(true);
            TextField kiText = (TextField) scene.lookup("#kiText");
            kiText.textProperty().bind(Bindings.format(
                    "%.0f Wiederholungen/Move (KI)",
                    kiSlider.valueProperty()
            ));
            kiSlider.valueProperty().addListener((obs, oldval, newVal) ->
                    kiSlider.setValue(newVal.intValue()));
        }

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleButtonRamdom.setToggleGroup(toggleGroup);
        toggleButtonMinMax.setToggleGroup(toggleGroup);
        toggleButtonMinMaxCooperative.setToggleGroup(toggleGroup);
        toggleButtonCorporate.setToggleGroup(toggleGroup);

        //Selectiert den Random button beim Aufruf der Scene
        toggleButtonRamdom.setSelected(true);
        gameMode = GameModes.random;

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wird beim betätigen des Buttons ausgeführt -> Erzeugt das Spielfenster
     *
     * @param event Event
     * @throws IOException
     */
    public void onButtonPressPlay(ActionEvent event) throws IOException {

        //Erzeuge eine Szene aus GameView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Game/GameView.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //WICHTIG: Bei databinding geht es nur so -> Es kann nicht alles (4 Zeilen oben drüber) ausgelagert werden loader.getControler ist notwendig.
        gameView = loader.getController();
        gameView.setAiMode(kiMode, (int) kiSlider.getValue());
        gameView.setTileCount(tileCount);
        gameView.setGameMode(gameMode);
        gameView.setWindowDimensions(windowWidth, windowHeight, minWindowWidth, minWindowHeight);
        gameView.createGameScene(event, scene);
    }

    /**
     * So wird die größe vom Menü übergeben.
     * Wird zum erzeugen des Spielfeld-Fensters benötigt.
     *
     * @param windowWidth Fensterhöhe
     * @param windowHeight Fensterbreite
     * @param minWindowWidth Maximale Fensterhöhe
     * @param minWindowHeight Maximale Fensterbreite
     */
    @Override
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight) {
        this.minWindowHeight = minWindowHeight;
        this.minWindowWidth = minWindowWidth;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
    }

    /**
     * Setzt die Spielfeldgröße.
     * Wird so vom Menü übergeben.
     *
     * @param tileCount Anzahl der Tiless
     */
    @Override
    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
    }

    /**
     * Setzt den Modus für die Ki auf Wahr oder Falsch.
     * Wir so vom Menü übergeben.
     *
     * @param kiMode Ki-Modus
     */
    @Override
    public void setKiMode(boolean kiMode) {
        this.kiMode = kiMode;
    }

    @Override
    public void setGamemodeRandom(){
        this.gameMode = GameModes.random;
    }

    @Override
    public void setGamemodeCooperative() {
        this.gameMode = GameModes.cooperative;
    }

    @Override
    public void setGamemodeMinMax(){
        this.gameMode = GameModes.minMax;
    }

    @Override
    public void setGamemodeMinMaxCooperative(){
        this.gameMode = GameModes.minMaxCooperative;
    }

}
