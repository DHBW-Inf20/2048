package View.ModusMenue;

import View.Game.GameView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    public void createModusMenueScene(Event event) throws IOException {

        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModusMenue/ModusMenueView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Übergebe dem Controler die notwendigen Daten
        ModusMenueView modusMenueView = loader.getController();
        modusMenueView.setWindowDimensions(windowWidth, windowHeight, minWindowWidth, minWindowHeight);
        modusMenueView.setKiMode(kiMode);
        modusMenueView.setTileCount(tileCount);

        //erstelle eine Togglegruppe für die Radiobuttons
        ToggleButton toggleButtonRamdom = (ToggleButton) scene.lookup("#buttonRandom");
        ToggleButton toggleButtonMinMax = (ToggleButton) scene.lookup("#buttonMinMax");
        ToggleButton toggleButtonCorporate = (ToggleButton) scene.lookup("#buttonCorporate");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleButtonRamdom.setToggleGroup(toggleGroup);
        toggleButtonMinMax.setToggleGroup(toggleGroup);
        toggleButtonCorporate.setToggleGroup(toggleGroup);

        toggleButtonRamdom.setSelected(true);

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Wird beim betätigen des Buttons ausgeführt -> Erzeugt das Spielfenster
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressPlay(ActionEvent event) throws IOException {

        //Erzeugt neue Instanz des Controlers und ruft Methode zum Erzeugen des Fesnters auf
        GameView gameView = new GameView();
        gameView.setWindowDimensions(windowWidth, windowHeight, minWindowWidth, minWindowHeight);
        gameView.setTileCount(tileCount);
        gameView.setKiMode(kiMode);
        gameView.createGameScene(event);
    }

    /**
     * So wird die größe vom Menü übergeben.
     * Wird zum erzeugen des Spielfeld-Fensters benötigt.
     *
     * @param windowWidth
     * @param windowHeight
     * @param minWindowWidth
     * @param minWindowHeight
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
     * @param tileCount
     */
    @Override
    public void setTileCount(int tileCount) {
        this.tileCount = tileCount;
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
