module FXTest {
    requires javafx.fxml;
    requires javafx.controls;
    opens View.Game;
    requires Controller;
    opens View.Menue;
}