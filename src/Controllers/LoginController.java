package Controllers;

import Database.DBHandler;
import javafx.event.ActionEvent;
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
    private void logIn(ActionEvent event) {

        DBHandler dbHandler = new DBHandler();
        dbHandler.getConnection();
        boolean checkIfExists = dbHandler.findUser(userEmail.getText(), userPassword.getText());
        if (checkIfExists) {
            SceneChanger.changeScene(event, "../Views/Marketplace.fxml", "HKR Marketplace");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Login credentials not found");
            alert.showAndWait();
        }
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
