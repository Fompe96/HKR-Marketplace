package Controllers;

import Database.DBHandler;
import Models.EmailSender;
import Models.SceneChanger;
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

public class RetrieveCredentialsController implements Initializable {
    private DBHandler dbHandler;
    @FXML
    private TextField userEmail;
    @FXML
    private AnchorPane root;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        Platform.runLater(() -> root.requestFocus());
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
    public void retrieveCredentialsButtonAction() {
        String userPassword = dbHandler.findUserPassword(userEmail.getText());
        if (userPassword != null) {
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(userEmail.getText(), "Retrieved login details", "Here are your account details" +
                    "\nEmail: " + userEmail.getText() + "\nPassword: " + userPassword);
            backButtonAction();
        } else {
            userEmail.setText("");
        }
    }

    @FXML
    private void backButtonAction() {
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    private void changeToContactSupport() {
        SceneChanger.changeScene("/Views/WriteToSupport.fxml");
    }
}