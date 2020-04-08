package Controllers;

import Models.EmailSender;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WriteToSupportController {

    private double x, y;

    @FXML
    private TextField subject;

    @FXML
    private TextArea userMessage;

    @FXML
    private TextField userEmail;

    @FXML
    private Button backButton;

    EmailSender emailSender = new EmailSender();

    @FXML
    private void handleClosingButton() {
        Platform.exit();
    }

    @FXML
    private void handleMinimizeButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void supportMessage() {
        if (userEmail.getText().equals("") || subject.getText().equals("") || userMessage.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter all fields!");
            alert.showAndWait();
        } else {
            if (!isValid(userEmail.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid email");
                alert.showAndWait();
            } else {
                emailSender.sendSupportMessage(userEmail.getText(), subject.getText(), userMessage.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Email sent");
                alert.setContentText("The email was sent to our support team. \n We will be in contact shortly.");
                alert.showAndWait();
                backButtonAction();
            }
        }
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
    private void backButtonAction() {
        SceneChanger.changeScene("/Views/Login.fxml", "");
    }

    private static boolean isValid(String userEmail) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }
}
