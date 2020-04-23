package Controllers;

import Database.DBHandler;
import Models.Account;
import Models.MessageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class InsertAccountController implements Initializable {
    @FXML
    private Button closebutton;
    @FXML
    private CheckBox truecheckbox, falsecheckbox;
    @FXML
    private TextField usernamefield, passwordfield, emailfield, uploadfield;
    private double x, y;
    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uploadfield.setEditable(false);
    }

    @FXML
    private void handleCheckboxes(ActionEvent event) {
        if (event.getSource() == truecheckbox) {
            falsecheckbox.setSelected(false);
        } else if (event.getSource() == falsecheckbox) {
            truecheckbox.setSelected(false);
        }
    }

    @FXML
    private void handleInsertButton() {
        if (!usernamefield.getText().equals("") && !passwordfield.getText().equals("") && !emailfield.getText().equals("")) {
            if (isValid(emailfield.getText()) && !DBHandler.seeIfEmailAlreadyRegistered(emailfield.getText())) {
                Account account = new Account(usernamefield.getText(), passwordfield.getText(), emailfield.getText(), getSelectedCheckbox(), imageFile);
                DBHandler.insertAccountIntoDB(account);
                MessageHandler.getInformationAlert("Success", "Success", "The account was successfully added to the database!").showAndWait();
                // Transfer account to tableview somehow
                handleClosingButton();
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "Email is either already in use or doesn't meet requirements.").showAndWait();
            }
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "You have to enter name, password and email!").showAndWait();
        }
    }

    private boolean getSelectedCheckbox() {
        return !falsecheckbox.isSelected();
    }

    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            uploadfield.setText(imageFile.getPath());
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "File does not exist!").showAndWait();
        }
    }

    private static boolean isValid(String userEmail) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return userEmail.matches(regex);
    }

    @FXML
    private void handleResetButton() {
        imageFile = null;
        uploadfield.setText("");
    }

    @FXML
    private void handleClosingButton() {
        Stage popupStage = (Stage) closebutton.getScene().getWindow();
        popupStage.close();
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
}
