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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RetrieveCredentialsController implements Initializable {
    private DBHandler dbHandler = new DBHandler();
    @FXML
    private TextField userEmail;
    private String userPassword;

    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        findUser(userEmail.getText());
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail(userEmail.getText(), "Retrieved login details", "Here are your account details" +
                "\nEmail: " + userEmail.getText() + "\nPassword: " + getUserPassword());
        backButtonAction();
    }


    public void findUser(String userEmail) {
        String query = "SELECT * FROM ACCOUNT WHERE Email = " + "'" + userEmail + "';";
        try (PreparedStatement statement = dbHandler.getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                setUserPassword(resultSet.getString(3));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @FXML
    private void backButtonAction() {
        SceneChanger.changeScene("../Views/Login.fxml", "Login Page");
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}