package View.Credits;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class CreditsView implements ICreditsView {

    private int windowWidth;
    private int windowHeight;
    private int minWindowWidth;
    private int minWindowHeight;

    public CreditsView() {


    }

    //TODO: Create Credits Scene Methode


    public void onButtonPressBack(ActionEvent event) throws IOException {

        //TODO: Back button funktioniert nicht

        //Erzeuge eine Szene aus ModusMenueView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Menue/MenueView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        //Erzeuge eine neue Stage für die GameView
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        //TODO: BACK BUTTON -> Funtioniert nur nach dem man das Winodow resized ... aber Warum?

    }

    @Override
    public void setWindowDimensions(int windowWidth, int windowHeight, int minWindowWidth, int minWindowHeight) {

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.minWindowWidth = minWindowWidth;
        this.minWindowHeight = minWindowHeight;
    }
}
