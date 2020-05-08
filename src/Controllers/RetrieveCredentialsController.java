package Controllers;

import Database.DBHandler;
import Models.EmailSender;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.ToolTipHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RetrieveCredentialsController implements Initializable {
    @FXML
    private TextField userEmail;
    @FXML
    private AnchorPane root;
    @FXML
    private Button closingButton, minimizeButton, backButton, retrieveButton;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> root.requestFocus());
        handleToolTip();
        handleCursor();
    }

    private void handleCursor() {
        backButton.setCursor(Cursor.HAND);
        retrieveButton.setCursor(Cursor.HAND);
    }

    private void handleToolTip() {
        ToolTipHandler.getToolTipCloseButton(closingButton);
        ToolTipHandler.getToolTipMinimizeButton(minimizeButton);

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
        String userPassword = DBHandler.findUserPassword(userEmail.getText());
        if (userPassword != null) {
            new Thread(() -> {
                EmailSender.sendEmail(userEmail.getText(), "Retrieved login details", "Here are your account details" +
                        "\nEmail: " + userEmail.getText() + "\nPassword: " + userPassword);
            }).start();
            MessageHandler.getConfirmationAlert("Success", "Success", "An email will be sent with your login credentials!").showAndWait();
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