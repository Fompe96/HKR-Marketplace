package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private TextField userPassword;

    @FXML
    private TextField userEmail;

    private double x, y;
    public DBHandler dbHandler = new DBHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler.createConnection();
    }

    @FXML
    private void handleClosingButton() {
        Platform.exit();
    }

    @FXML
    private void handleMinimizeButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    void windowDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    void windowPressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    private void logInButtonAction() {

        boolean checkIfExists = dbHandler.findUser(userEmail.getText(), userPassword.getText());
        if (checkIfExists) {
            Singleton.getInstance().setLoggedInUser(dbHandler.getUserInformation(userEmail.getText()));
            SceneChanger.changeScene("../Views/Marketplace.fxml", "HKR Marketplace");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Login credentials not found");
            alert.showAndWait();
        }
    }

    @FXML
    private void registerButtonAction() {
        SceneChanger.changeScene("../Views/SignUp.fxml", "Sign Up");
    }

    @FXML
    private void retrieveCredentialsButtonAction() {
        SceneChanger.changeScene("../Views/RetrieveCredentials.fxml", "Retrieve Credentials");
    }
}