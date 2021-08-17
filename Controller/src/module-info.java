module Controller {
    requires javafx.fxml;
    requires javafx.controls;
    exports Game;
    exports Game.DataClasses;
    exports HighScore;
    exports Menu;
    exports Game.Listeners;
}