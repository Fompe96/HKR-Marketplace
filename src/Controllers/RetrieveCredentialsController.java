package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RetrieveCredentialsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        SceneChanger.changeScene("../Views/Login.fxml", "Login Page");
    }
}
