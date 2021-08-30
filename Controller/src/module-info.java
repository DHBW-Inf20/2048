module Controller {
    requires javafx.fxml;
    requires javafx.controls;
    requires unirest.java;
    requires httpclient;
    requires json;
    exports Game;
    exports DataClasses;
    exports HighScore;
    exports Game.Listeners;
    exports AI;
    exports Game.TileCreator;
    exports PlayerData;
}