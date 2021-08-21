module Controller {
    requires javafx.fxml;
    requires javafx.controls;
    exports Game;
    exports DataClasses;
    exports HighScore;
    exports Game.Listeners;
    exports AI;
    exports Game.TileCreator;
    exports PlayerData;
}