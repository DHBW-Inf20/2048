module FXTest {
    requires javafx.fxml;
    requires javafx.controls;
    requires Controller;
    opens View.Game;
    opens View.Menue;
    opens View.ModusMenue;
    opens View.Credits;
    opens View.Highscore;
}