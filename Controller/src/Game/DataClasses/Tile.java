package Game.DataClasses;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Tile {

    //Einstellbare Konstanten für die Fenstergröße
    private final int windowWidth = 600;
    private final int windowHeight = 800;

    private int number;
    private Tile preFieldA;
    private Tile preFieldB;

    private int posX;
    private int posY;

    public StackPane stackPane;


    public Tile(int number, Tile preFieldA, Tile preFieldB, int posX, int posY) {
        this.number = number;
        this.preFieldA = preFieldA;
        this.preFieldB = preFieldB;
        this.posX = posX;
        this.posY = posY;

        if (preFieldA != null) preFieldA.clearPreTiles();
        if (preFieldB != null) preFieldB.clearPreTiles();

        int tileCount = 4;

        //TODO: Diese Werte müssen irgendwie von GameView aus gesetzt werden ... ggf. erst an den Tilecreator übergeben werden und dann beim Instanzieren hier gesetzt werden
        //Variablen mit einstellbaren Konstanten
        double gameBoardSize = 450;
        double gameBoardGap = gameBoardSize * 0.02;
        double tileSize = (gameBoardSize - (gameBoardGap * (tileCount + 1))) / tileCount;

        this.stackPane = new StackPane();
        stackPane.setPrefWidth(tileSize);
        stackPane.setPrefHeight(tileSize);

        stackPane.setId("stackPane" + number);

        Text text = new Text(String.valueOf(number));
        text.setId("tileText" + number);
        stackPane.getChildren().add(text);

        //TODO: Zahl einfügen und Farbe je nach Zahl anpassen

    }


    public void clearPreTiles() {
        this.preFieldA = null;
        this.preFieldB = null;
    }

    public Pane getPane() {
        return stackPane;
    }

    public int getNumber() {
        return number;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Tile getPreFieldA() {
        return preFieldA;
    }

    public Tile getPreFieldB() {
        return preFieldB;
    }

    public boolean checkForPreTiles() {
        return preFieldA == null && preFieldB == null;
    }

    public void setPosition(int posX, int posY){
        //von Felix zum GUI Testen ... um Testfelder zu erzeugen und deren Position setzen (von GameView aus)
        //Kann später entferne werden wenn die Logik funktioniert
        this.posX = posX;
        this.posY = posY;
    }
}
