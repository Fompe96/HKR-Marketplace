package Controllers;


import Database.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userPassword;

    @FXML
    private TextField userName;

    private int idAccount;

    private DBHandler dbHandler;
    private Connection dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbHandler = new DBHandler();
    }

    @FXML
    private void backButtonAction(ActionEvent event) {
        SceneChanger.changeScene("../Views/Login.fxml", "Login Page");
    }

    @FXML
    private void registerButton(ActionEvent event) {
        if (userName.getText().equals("") || userEmail.getText().equals("") || userPassword.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter all fields to register!");
            alert.showAndWait();
        } else {
            if (!isValid(userEmail.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid email");
                alert.showAndWait();
            } else {
                dbConnection = dbHandler.getConnection();
                try {
                    if (dbHandler.seeIfEmailAlreadyRegistered(userEmail.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Email already registered");
                        alert.showAndWait();
                    } else {
                        PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`account` (`idAccount`, `Username`, `Password`, `Email`, `Admin`) VALUES (?, ?, ?, ?, ?);\n");
                        PreparedStatement count = dbConnection.prepareStatement("select count(idAccount) from account;");
                        ResultSet rs = count.executeQuery();
                        while (rs.next()) { //Finds the amount of idAccount since its PK
                            int idAccount = rs.getInt(1);
                            setIdAccount(idAccount);
                        }
                        stmt.setInt(1, getIdAccount() + 1); //Gets the amount of idAccount and adds one
                        stmt.setString(2, userName.getText());
                        stmt.setString(3, userPassword.getText());
                        stmt.setString(4, userEmail.getText());
                        stmt.setBoolean(5, false);
                        stmt.executeUpdate();
                        dbHandler.closeConnection();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Your account has now been registered! \nYou will receive an email with login credentials");
                        alert.showAndWait();

                        EmailSender emailSender = new EmailSender();
                        emailSender.sendEmail(userEmail.getText(), "Your new account", "Welcome to HKR Marketplace! Here are your account details. \n \n" +
                                "Username: " + userName.getText() + "\n" + "Password: " + userPassword.getText() + "\n" + "Account-Email: " + userEmail.getText());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    static boolean isValid(String userEmail) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }


    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
}
