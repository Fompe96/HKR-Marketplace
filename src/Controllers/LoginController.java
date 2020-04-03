package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {


    @FXML
    public void logIn(ActionEvent event) {

        //if (retrieveCredentials) kalla sceneChanger och byt scene.
        SceneChanger.changeScene(event, "../Views/Marketplace.fxml", "HKR Marketplace");
    }

    public void register() {

    }

    public void retrieveCredentials() {
    }
}
