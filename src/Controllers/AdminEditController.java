package Controllers;

import Models.Account;
import Models.Item;
import Models.MessageHandler;
import Models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminEditController implements Initializable {
    private double x, y;
    private boolean editAccount;
    @FXML
    private Label titletext;
    @FXML
    private Button closebutton;
    @FXML
    private Pane editAccountsPane, editItemsPane;
    @FXML
    private TextField oldnamefield, oldpasswordfield, oldemailfield, oldadminfield,
            newnamefield, newpasswordfield, newemailfield, newadminfield, accountuploadfield,
            olditemname, olditemprice, olditemcondition, olditemcategory, olditemowner, olditemactive;

    @FXML
    private ImageView oldimageview, olditemimageview;

    @FXML
    private TextArea olditemdescription;
    Account account;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkObjectToEdit();
        setSceneVisuals();
    }

    private void checkObjectToEdit() {
        if (Singleton.getInstance().getObjectToEdit() instanceof Account) {
            editAccount = true;
        } else if (Singleton.getInstance().getObjectToEdit() instanceof Item) {
            editAccount = false;
        }
    }

    private void setSceneVisuals() {
        if (editAccount) {
            titletext.setText("Account");
            editAccountsPane.setVisible(true);
            editItemsPane.setVisible(false);
            displayOldAccount();
            fillNewAccountFields();
        } else {
            titletext.setText("Item");
            editItemsPane.setVisible(true);
            editAccountsPane.setVisible(false);
            displayOldItem();
        }
    }

    private void displayOldAccount() {
        account = (Account) Singleton.getInstance().getObjectToEdit();
        oldnamefield.setText(account.getUserName());
        oldpasswordfield.setText(account.getPassword());
        oldemailfield.setText(account.getEmail());
        if (account.isAdmin()) {
            oldadminfield.setText("True");
        } else {
            oldadminfield.setText("False");
        }
        if (account.getImage() != null) {
            try {
                oldimageview.setImage(account.getImage());
            } catch (NullPointerException e) {
                MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to display the picture.").showAndWait();
            }
        }
    }

    private void fillNewAccountFields() {
        newnamefield.setText(oldnamefield.getText());
        newpasswordfield.setText(oldpasswordfield.getText());
        newemailfield.setText(oldemailfield.getText());
        newadminfield.setText(oldadminfield.getText());
        accountuploadfield.setText(account.getImageFile().getPath());
    }

    private void displayOldItem() {
        Item item = (Item) Singleton.getInstance().getObjectToEdit();
        olditemname.setText(item.getName());
        olditemprice.setText(String.valueOf(item.getPrice()));
        olditemdescription.setText(item.getDescription());
        olditemcondition.setText(item.getCondition());
        olditemcategory.setText(item.getCategory());
        olditemowner.setText(item.getOwner());
        if (item.getImage() != null) {
            try {
                olditemimageview.setImage(item.getImage());
            } catch (NullPointerException e) {
                MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to display the picture.").showAndWait();
            }
        }
        if (item.isSaleActive()) {
            olditemactive.setText("True");
        } else {
            olditemactive.setText("False");
        }
    }

    @FXML
    private void handleUploadButton() {

    }

    @FXML
    private void handleResetButton() {

    }

    @FXML
    private void handleUpdateButton() {

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
