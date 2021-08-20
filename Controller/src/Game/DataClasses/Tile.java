package Game.DataClasses;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
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


    public Tile(int number, Tile preFieldA, Tile preFieldB, int posX, int posY, double tileSize, int tileCount) {
        this.number = number;
        this.preFieldA = preFieldA;
        this.preFieldB = preFieldB;
        this.posX = posX;
        this.posY = posY;

        if (preFieldA != null) preFieldA.clearPreTiles();
        if (preFieldB != null) preFieldB.clearPreTiles();

        this.stackPane = new StackPane();
        stackPane.setPrefWidth(tileSize);
        stackPane.setPrefHeight(tileSize);

        stackPane.setId("stackPane" + number);

        Text text = new Text(String.valueOf(number));

        //Mehr Tiles -> kleinerer Text
        switch (tileCount) {
            case 5:
                text.setStyle("-fx-font: 25pt Helvetica;");
                break;
            case 6:
            case 7:
                text.setStyle("-fx-font: 20pt Helvetica;");
                break;
            case 8:
                text.setStyle("-fx-font: 15pt Helvetica;");
                break;
            case 9:
                text.setStyle("-fx-font: 13pt Helvetica;");
                break;
            case 10:
                text.setStyle("-fx-font: 11pt Helvetica;");
                break;
            default:
                break;
        }

        text.setId("tileText" + number);
        stackPane.getChildren().add(text);

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
