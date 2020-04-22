package Controllers;


import Database.DBHandler;
import Models.EmailSender;
import Models.MessageHandler;
import Models.SceneChanger;
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
    private Button confirmEmailButton;
    private int uniqueRegistrationNumber;

    private DBHandler dbHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("test");
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
    private void backButtonAction() {
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    private void generateUniqueNumber(String userEmail) {
        uniqueRegistrationNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
        EmailSender emailSender = new EmailSender();
        emailSender.sendValidationEmail(userEmail, uniqueRegistrationNumber);
    }

    @FXML
    private void signUpButtonAction() {
        if (userName.getText().equals("") || userEmail.getText().equals("") || userPassword.getText().equals("")) {
            MessageHandler.getErrorAlert("Error", "Error", "Please enter all fields to register!").showAndWait();
        } else {
            if (!userPassword.getText().equals(confirmPassword.getText())) {
                MessageHandler.getErrorAlert("Error", "Error", "Passwords do not match, please re-enter").showAndWait();
                userPassword.clear();
                confirmPassword.clear();
            } else {
                if (!isValid(userEmail.getText())) {
                    MessageHandler.getErrorAlert("Error", "Error", "Please enter a valid email").showAndWait();
                } else {
                    if (dbHandler.seeIfEmailAlreadyRegistered(userEmail.getText())) {
                        MessageHandler.getErrorAlert("Error", "Error", "Email already registered").showAndWait();
                    } else {
                        generateUniqueNumber(userEmail.getText());
                        validateEmailField.setOpacity(100);
                        confirmEmailButton.setOpacity(100);
                    }
                }
            }
        }
    }

    private boolean validateUniqueNumberGiven(int uniqueNumber) {
        return uniqueNumber == uniqueRegistrationNumber;
    }

    @FXML
    private void insertUserIntoDatabase() throws SQLException {
        if (validateUniqueNumberGiven(Integer.parseInt(validateEmailField.getText()))) {
            Connection dbConnection = dbHandler.getConnection();
            PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`account` (`Username`, `Password`, `Email`, `Admin`) VALUES (?, ?, ?, ?);\n");

            stmt.setString(1, userName.getText());
            stmt.setString(2, userPassword.getText());
            stmt.setString(3, userEmail.getText());
            stmt.setBoolean(4, false);
            stmt.executeUpdate();
            dbHandler.closeConnection();

            Image image = new Image("Resources/Success.gif");
            madeAccount.setOpacity(100);
            madeAccount.setImage(image);
            MessageHandler.getInformationAlert("Success", "Information", "Your account has now been registered! \nYou will receive an email with login credentials").showAndWait();

            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(userEmail.getText(), "Your new account", "Welcome to HKR Marketplace! Here are your account details. \n \n" +
                    "Username: " + userName.getText() + "\n" + "Password: " + userPassword.getText() + "\n" + "Account-Email: " + userEmail.getText());
            backButtonAction();
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "The pin provided was not correct!").showAndWait();
        }
    }


    private static boolean isValid(String userEmail) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }
}