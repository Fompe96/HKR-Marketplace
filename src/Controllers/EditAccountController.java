package Controllers;

import Database.DBHandler;
import Models.Account;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.Objects;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditAccountController implements Initializable {
    private double x, y;
    private boolean changeDone;
    @FXML
    private Button closebutton;
    @FXML
    private TextField oldnamefield, oldpasswordfield, oldemailfield, oldadminfield,
            newnamefield, newpasswordfield, newemailfield, accountuploadfield;
    @FXML
    private ImageView oldimageview;
    @FXML
    private CheckBox truebox, falsebox;
    private File newImage;
    private Account accountToEdit;
    private Account newAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayOldAccount();
        fillNewAccountFields();
        newImage = accountToEdit.getImageFile();
    }

    private void displayOldAccount() {
        accountToEdit = (Account) Singleton.getInstance().getObjectToEdit();
        oldnamefield.setText(accountToEdit.getUserName());
        oldpasswordfield.setText(accountToEdit.getPassword());
        oldemailfield.setText(accountToEdit.getEmail());
        if (accountToEdit.isAdmin()) {
            oldadminfield.setText("True");
            truebox.setSelected(true);
            falsebox.setSelected(false);
        } else {
            oldadminfield.setText("False");
            falsebox.setSelected(true);
            truebox.setSelected(false);
        }
        if (accountToEdit.getImageFile() != null) {
            try {
                oldimageview.setImage(accountToEdit.getImage());
            } catch (NullPointerException e) {
                MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to display the picture.").showAndWait();
            }
        }
    }

    private void fillNewAccountFields() {
        newImage = accountToEdit.getImageFile();
        newnamefield.setText(oldnamefield.getText());
        newpasswordfield.setText(oldpasswordfield.getText());
        newemailfield.setText(oldemailfield.getText());
        if (accountToEdit.isAdmin()) {
            truebox.setSelected(true);
            falsebox.setSelected(false);
        } else {
            truebox.setSelected(false);
            falsebox.setSelected(true);
        }
        if (newImage != null) {
            accountuploadfield.setText(newImage.getPath());
        } else {
            accountuploadfield.setText("Null");
        }
    }

    @FXML
    private void handleCheckboxes(ActionEvent event) {
        if (event.getSource() == truebox) {
            truebox.setSelected(true);
            falsebox.setSelected(false);
        } else if (event.getSource() == falsebox) {
            falsebox.setSelected(true);
            truebox.setSelected(false);
        }
    }

    private boolean getSelectedCheckBox() {
        return truebox.isSelected();
    }

    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        newImage = fileChooser.showOpenDialog(null);
        if (newImage != null) {
            accountuploadfield.setText(newImage.getPath());
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "File does not exist!").showAndWait();
            accountuploadfield.setText("Null");
        }
    }

    @FXML
    private void handleResetButton() {
        newImage = null;
        accountuploadfield.setText("Null");
    }

    @FXML
    private void handleUpdateButton() {
        updateAccount();
    }

    private void updateAccount() {
        newAccount = new Account(accountToEdit.getUserName(), accountToEdit.getPassword(), accountToEdit.getEmail(), accountToEdit.isAdmin(), accountToEdit.getImageFile());
        boolean validationChecker = true;
        changeDone = false;
        if (!newnamefield.getText().equals(oldnamefield.getText())) {
            validationChecker = validateNewAccountName();
        }
        if (validationChecker && !oldpasswordfield.getText().equals(newpasswordfield.getText())) {
            validationChecker = validateNewPassword();
        }
        if (validationChecker && !oldemailfield.getText().equals(newemailfield.getText())) {
            validationChecker = validateNewEmail();
        }
        if (validationChecker && !Objects.equals(accountToEdit.isAdmin(), getSelectedCheckBox())) {
            validationChecker = validateNewAdminStatus();
        }
        if (validationChecker && !Objects.equals(accountToEdit.getImageFile(), newImage)) {
            validationChecker = validateNewImage();
        }

        if (validationChecker && changeDone) {    // If all checks performed are passed the values are changed.
            newAccount.setUserName(newnamefield.getText());
            newAccount.setPassword(newpasswordfield.getText());
            newAccount.setEmail(newemailfield.getText());
            if (getSelectedCheckBox()) {
                newAccount.setAdmin(true);
            } else {
                newAccount.setAdmin(false);
            }
            newAccount.setImageFile(newImage);
            DBHandler.updateAccountInformation(accountToEdit, newAccount);
            SceneChanger.changeScene("../Views/Administration.fxml");
            handleClosingButton();
        }
    }

    private boolean validateNewAccountName() {
        if (!newnamefield.getText().equals("") && newnamefield.getText().length() > 2) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "New name doesn't meet requirements.").showAndWait();
            return false;
        }
    }

    private boolean validateNewPassword() {
        if (!newpasswordfield.getText().equals("") && newpasswordfield.getText().length() > 2) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "New password doesn't meet requirements.").showAndWait();
            return false;
        }
    }

    private boolean validateNewEmail() {
        if (Account.validateEmail(newemailfield.getText()) && !DBHandler.seeIfEmailAlreadyRegistered(newemailfield.getText())) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "New email doesn't meet requirements.").showAndWait();
            return false;
        }
    }

    private boolean validateNewAdminStatus() {
        changeDone = true;
        return true;
    }

    private boolean validateNewImage() {
      if (!Objects.equals(accountToEdit.getImageFile(), newImage)) {
          changeDone = true;
          return true;
      } else {
          MessageHandler.getErrorAlert("Error", "Error", "Something went wrong with picture.").showAndWait();
          return false;
      }
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
