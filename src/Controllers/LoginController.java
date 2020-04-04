package Controllers;

import Database.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private TextField userPassword;

    @FXML
    private TextField userEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void logInButtonAction() {

        DBHandler dbHandler = new DBHandler();
        dbHandler.getConnection();
        boolean checkIfExists = dbHandler.findUser(userEmail.getText(), userPassword.getText());
        if (checkIfExists) {
            SceneChanger.changeScene("../Views/Marketplace.fxml", "HKR Marketplace");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Login credentials not found");
            alert.showAndWait();
        }
        dbHandler.closeConnection();
    }

    @FXML
    private void registerButtonAction() {
        SceneChanger.changeScene("../Views/Register.fxml", "Register account");
    }

    @FXML
    private void retrieveCredentialsButtonAction() {
        SceneChanger.changeScene("../Views/RetrieveCredentials.fxml", "Retrieve Credentials");
    }
}