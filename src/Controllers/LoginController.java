package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void logIn(ActionEvent event) {

        //if (retrieveCredentials) kalla sceneChanger och byt scene.
        SceneChanger.changeScene(event, "../Views/Marketplace.fxml", "HKR Marketplace");
    }

    @FXML
    private void registerButtonAction(ActionEvent event) {
        SceneChanger.changeScene(event, "../Views/Register.fxml", "Retrieve Credentials");
    }

    @FXML
    private void retrieveCredentialsButtonAction(ActionEvent event) {
        SceneChanger.changeScene(event, "../Views/RetrieveCredentials.fxml", "Retrieve Credentials");
    }


}
