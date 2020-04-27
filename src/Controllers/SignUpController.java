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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class SignUpController implements Initializable {

    @FXML
    private TextField userEmail, userName, userPassword, confirmPassword;
    private double x, y;
    @FXML
    private ImageView madeAccount;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField validateEmailField;
    @FXML
    private Button confirmEmailButton, closingButton, minimizeButton;
    private int uniqueRegistrationNumber;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> root.requestFocus());
        handleToolTip();
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
    private void backButtonAction() {
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    private void generateUniqueNumber(String userEmail) {
        uniqueRegistrationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
        new Thread(() -> {
            EmailSender.sendValidationEmail(userEmail, uniqueRegistrationNumber);
        }).start();
    }

    @FXML
    private void signUpButtonAction() {
        new Thread(() -> {
            if (userName.getText().equals("") || userEmail.getText().equals("") || userPassword.getText().equals("")) {
                Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Please enter all fields to register!").showAndWait());
            } else {
                if (!userPassword.getText().equals(confirmPassword.getText())) {
                    Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Passwords do not match, please re-enter").showAndWait());
                    userPassword.clear();
                    confirmPassword.clear();
                } else {
                    if (!isValid(userEmail.getText())) {
                        Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Please enter a valid email").showAndWait());
                    } else {
                        if (DBHandler.seeIfEmailAlreadyRegistered(userEmail.getText())) {
                            Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Email already registered").showAndWait());
                        } else {
                            generateUniqueNumber(userEmail.getText());
                            validateEmailField.setOpacity(100);
                            confirmEmailButton.setOpacity(100);
                        }
                    }
                }
            }
        }).start();
    }

    private boolean validateUniqueNumberGiven(int uniqueNumber) {
        return uniqueNumber == uniqueRegistrationNumber;
    }

    @FXML
    private void registerUser() {
        new Thread(() -> {
            if (validateUniqueNumberGiven(Integer.parseInt(validateEmailField.getText()))) {
                try {
                    DBHandler.insertUserIntoDatabase(userName.getText(), userPassword.getText(), userEmail.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::backButtonAction);
            }
        }).start();
    }


    private static boolean isValid(String userEmail) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }
}