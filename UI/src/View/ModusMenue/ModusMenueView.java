package View.ModusMenue;

import View.Game.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public ModusMenueView() {

    }


    /**
     * Wird beim betätigen des Buttons ausgeführt
     *
     * @param event
     * @throws IOException
     */
    public void onButtonPressPlay(ActionEvent event) throws IOException {

        //TODO: Ist es Klug alles an der gameBoard Size festzumachen?

        //Variablen mit einstellbaren Konstanten
        double gameBoardSize = 450;
        double gameBoardGap = gameBoardSize * 0.02;
        double tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;

        //Erzeuge eine Szene aus GameView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Game/GameView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Übergebe dem Controler die notwendigen Daten
        GameView gameViewModel = loader.getController();
        gameViewModel.setTileCount(tileCount);

        //Hole das Pane(/board) aus der .fxml anhand der ID
        Pane pane = (Pane) scene.lookup("#board");

        //Setze die größe des Panes auf die vorgegebenen
        pane.setPrefSize(gameBoardSize, gameBoardSize);
        pane.setMinHeight(gameBoardSize);
        pane.setMaxHeight(gameBoardSize);
        pane.setMinWidth(gameBoardSize);
        pane.setMaxWidth(gameBoardSize);

        //TODO: Restiliche Elemente Hinzufügen

        //TODO: Größe anpassen!!!!

        //Legt die größen der Elemente in Abhängigkeit der größe der Spielfelds fest
        HBox hBox = (HBox) scene.lookup("#gameBoardHBox");
        hBox.setPrefWidth(gameBoardSize);
        hBox.setMinWidth(gameBoardSize);

        VBox vBoxL = (VBox) scene.lookup("#gameBoardBVBoxL");
        vBoxL.setPrefWidth(gameBoardSize / 2);
        vBoxL.setMinWidth(gameBoardSize / 2);

        VBox vBoxR = (VBox) scene.lookup("#gameBoardBVBoxR");
        vBoxR.setPrefWidth(gameBoardSize / 2);
        vBoxR.setMinWidth(gameBoardSize / 2);

        //Erzeuge die Hintergurnd Tiles wie bei einem 2D-Array
        for (int j = 0; j < tileCount; j++) {
            for (int k = 0; k < tileCount; k++) {

                //Errechne die Position der einzelnen Tiles
                double posX = gameBoardGap * (j + 1) + (tileSize * j);
                double posY = gameBoardGap * (k + 1) + (tileSize * k);

                //Erzeuge Rechteck für die Tiles
                Rectangle rectangle = new Rectangle(posX, posY, tileSize, tileSize);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                rectangle.setFill(Color.rgb(204, 192, 179));

                //rectangle.setStyle("-fx-background-color: \"#ccc0b3\"; -fx-background-radius: 50 50 50 50; -fx-border-radius: 50 50 50 50;");
                pane.getChildren().add(rectangle);
            }
        }

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
