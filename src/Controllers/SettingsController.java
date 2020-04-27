package Controllers;

import Database.DBHandler;
import Models.EmailSender;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Label loggedInAs;
    @FXML
    private TextField currentPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private TextField reTypePasswordTextField;
    @FXML
    private Pane profilePicturePane;
    @FXML
    private Pane changePasswordPane;
    private double x, y;

    private File file;

    @FXML
    private ImageView imageToUpload;

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
    private void handleUploadImage() throws FileNotFoundException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String user = Singleton.getInstance().getLoggedInEmail();
            DBHandler.uploadImage(user, file.getPath());
            MessageHandler.getConfirmationAlert("Success", "Success", "Profile picture has been uploaded!").showAndWait();
            Image fxImage = new Image(new File(file.getPath()).toURI().toString());
            imageToUpload.setImage(fxImage);
        }
    }


    @FXML
    private void handleMarketButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }

    @FXML
    public void sellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Singleton.getInstance().setLoggedInAccount(DBHandler.getUserInformation(Singleton.getInstance().getLoggedInEmail()));
        loggedInAs.setText(Singleton.getInstance().getLoggedInName());
        DBHandler.getConnection();
        try {
            getProfilePicture();
        } catch (NullPointerException | SQLException ignored) {
        }
    }

    private void getProfilePicture() throws SQLException {
        String email = Singleton.getInstance().getLoggedInEmail();
        String SQL = "select Picture from account where Email = ?;";
        PreparedStatement pstmt = DBHandler.getConnection().prepareStatement(SQL);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            InputStream imageFile = rs.getBinaryStream(1);
            Image image = new Image(imageFile);
            imageToUpload.setImage(image);
        }
        DBHandler.closeConnection();
    }

    @FXML
    private void changePassword() throws SQLException {
        String currentPassword = Singleton.getInstance().getLoggedInAccount().getPassword();
        if (!currentPassword.equals(currentPasswordTextField.getText())) {
            MessageHandler.getErrorAlert("Error", "Error", "[Current Password] is not correct").showAndWait();
        } else {
            if (!newPasswordTextField.getText().equals(reTypePasswordTextField.getText())) {
                MessageHandler.getErrorAlert("Error", "Error", "New password does not match Re-enter password").showAndWait();
            } else {
                if (currentPassword.equals(newPasswordTextField.getText())) {
                    MessageHandler.getErrorAlert("Error", "Error", "New password cant be the same as your current password").showAndWait();
                } else {
                    DBHandler.changeUserPassword(Singleton.getInstance().getLoggedInEmail(), newPasswordTextField.getText());
                    changePasswordPane.setEffect(new GaussianBlur());
                    profilePicturePane.setEffect(new GaussianBlur());
                    MessageHandler.getConfirmationAlert("Success", "Success", "Your password has now been changed!").showAndWait();
                    new Thread(() -> {
                        EmailSender.sendUpdatedUserCredentialsEmail(Singleton.getInstance().getLoggedInEmail(), newPasswordTextField.getText());
                    }).start();
                    backButtonAction();
                }
            }
        }
    }

    @FXML
    private void backButtonAction() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }
}
