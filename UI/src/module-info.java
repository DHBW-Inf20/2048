module FXTest {
    requires javafx.fxml;
    requires javafx.controls;
    opens ViewModel.Game;
    opens ViewModel.Menue;
    opens View;
}