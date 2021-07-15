package Game.DataClasses;

import javafx.scene.layout.Pane;

public class Tile {

    //Einstellbare Konstanten für die Fenstergröße
    private final int windowWidth = 600;
    private final int windowHeight = 800;

    private int number;
    private Tile preFieldA;
    private Tile preFieldB;

    private int posX;
    private int posY;

    public Pane pane;


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

        this.pane = new Pane();
        pane.setPrefWidth(tileSize);
        pane.setPrefHeight(tileSize);
        pane.setId("pane");

        //TODO: Zahl einfügen und Farbe je nach Zahl anpassen
    }


    public void clearPreTiles() {
        this.preFieldA = null;
        this.preFieldB = null;
    }

    public Pane getPane() {
        return pane;
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
