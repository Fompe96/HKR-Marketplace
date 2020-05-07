package Controllers;

import Database.DBHandler;
import Models.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.AuthenticationFailedException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactSellerController implements Initializable {

    @FXML
    PasswordField password;

    @FXML
    TextField subject;

    @FXML
    TextArea message;

    private ObservableList<Account> accounts;

    private double x,y;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label rating;

    @FXML
    private ImageView profilePic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeScene();

    }


    public void initializeScene(){
        Account itemOwner = DBHandler.findUser(Singleton.getInstance().getItem().getOwner());
        subject.appendText("In regards of your " + Singleton.getInstance().getItem().getName());
        message.appendText("Hi my name is " + Singleton.getInstance().getLoggedInName()+ "," + "\n" + "and i am interested in your "
        + Singleton.getInstance().getItem().getName() + "\n" + "Please contact me," + "\n" + "my tel nr. is: " + "\n" + "and my email is " + Singleton.getInstance().getLoggedInEmail());

        if (Singleton.getInstance().getItem() != null && itemOwner != null) {
            if (itemOwner.getEmail().equals(Singleton.getInstance().getItem().getOwner())) {
                name.setText(itemOwner.getUserName());
                email.setText(itemOwner.getEmail());
                try {
                    profilePic.setImage(itemOwner.getImage());
                } catch (NullPointerException e) {
                }
            }
            rating.setText("6.9");
        }
    }

    public void goBackToItem() {
        SceneChanger.changeScene("../Views/ItemView.fxml");
    }

    public void sendEmail() {
        if (subject != null && message != null) {
            EmailSender.sendEmail(Singleton.getInstance().getItem().getOwner(), subject.getText(), message.getText());
            MessageHandler.getConfirmationAlert("Message sent", "Your message was sent", "Your message to " +
                    Singleton.getInstance().getItem().getOwner() + " was successfully sent").showAndWait();
        }else {
            MessageHandler.getErrorAlert("Missing text in testfield", "Missing text in testfield", "You have not entered a message or subject in the fields").showAndWait();

        }

    }

    @FXML
    private void handleLogOutButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    public void sellButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Sell.fxml");
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
    private void handleSettingsButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Settings.fxml");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml");
    }

    @FXML
    private void handleMonitoringButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/MonitoringSales.fxml");
    }


}
