package Controllers;

import Models.EmailSender;
import Models.MessageHandler;
import Models.SceneChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
            MessageHandler.getErrorAlert("Error", "Error","Please enter all fields!" ).showAndWait();
        } else {
            if (!isValid(userEmail.getText())) {
                MessageHandler.getErrorAlert("Error", "Error", "Please enter a valid email").showAndWait();
            } else {
                emailSender.sendSupportMessage(userEmail.getText(), subject.getText(), userMessage.getText());
                emailSender.sendEmail(userEmail.getText(), "Support has recieved your email", "Thanks for reaching out " +
                        "to our support team. A ticket has been created for your case and is being resolved as soon as possible. \n\n\n " +
                        "Please do not respond to this email as its an automatic email being sent out.");
                MessageHandler.getInformationAlert("Email sent", "Information", "The email was sent to our support team. \n We will be in contact shortly.").showAndWait();
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
        SceneChanger.changeScene("/Views/Login.fxml");
    }

    private static boolean isValid(String userEmail) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }
}
