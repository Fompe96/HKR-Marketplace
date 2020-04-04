package Controllers;

import Database.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void findUserCredentials() {
        findUser(userEmail.getText());
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail(userEmail.getText(), "Retrieved login details", "Here are your account details" +
                "\nEmail: " + userEmail.getText() + "\nPassword: " + getUserPassword());
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
    private void backButtonAction(ActionEvent event) {
        SceneChanger.changeScene(event, "../Views/Login.fxml", "Login Page");
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
