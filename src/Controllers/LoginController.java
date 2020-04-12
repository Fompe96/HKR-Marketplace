package Controllers;

import Database.DBHandler;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private TextField userPassword;

    @FXML
    private TextField userEmail;

    @FXML
    private AnchorPane root;

    private double x, y;
    private DBHandler dbHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        Platform.runLater( () -> root.requestFocus() );
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
        if (userEmail.getText().equals("") || userPassword.getText().equals("")) {
            MessageHandler.getErrorAlert("Error", "Error", "Please enter all fields").showAndWait();
        } else {
            dbHandler.getConnection();
            boolean checkIfExists = dbHandler.findUser(userEmail.getText(), userPassword.getText());
            if (checkIfExists) {
                Singleton.getInstance().setLoggedInAccount(dbHandler.getUserInformation(userEmail.getText()));
                SceneChanger.changeScene("../Views/Marketplace.fxml", "HKR Marketplace");
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "Login credentials not found");
            }
            dbHandler.closeConnection();
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