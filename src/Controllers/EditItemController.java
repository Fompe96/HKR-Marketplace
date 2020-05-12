package Controllers;

import Models.Item;
import Models.MessageHandler;
import Models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditItemController implements Initializable {
    @FXML
    private TextArea oldItemDescription;
    @FXML
    private TextField oldItemName, oldItemPrice, oldItemCondition, oldItemCategory, oldItemOwner, oldItemActive;
    @FXML
    private ImageView oldItemImageView;
    @FXML
    private Button closeButton;
    private double x,y;
    private Item itemToEdit;
    private File newImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayOldItem();
    }
    private void displayOldItem() {
        itemToEdit = (Item) Singleton.getInstance().getObjectToEdit();
        oldItemName.setText(itemToEdit.getName());
        oldItemPrice.setText(String.valueOf(itemToEdit.getPrice()));
        oldItemDescription.setText(itemToEdit.getDescription());
        oldItemCondition.setText(itemToEdit.getCondition());
        oldItemCategory.setText(itemToEdit.getCategory());
        oldItemOwner.setText(itemToEdit.getOwner());
        if (itemToEdit.getImage() != null) {
            try {
                oldItemImageView.setImage(itemToEdit.getImage());
            } catch (NullPointerException e) {
                MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to display the picture.").showAndWait();
            }
        }
        if (itemToEdit.isSaleActive()) {
            oldItemActive.setText("True");
        } else {
            oldItemActive.setText("False");
        }
    }

    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        newImage = fileChooser.showOpenDialog(null);
        if (newImage != null) {
            //itemuploadfield.setText(newImage.getPath());      NOT YET ADDED
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "File does not exist!").showAndWait();
        }
    }

    @FXML
    private void handleResetButton() {

    }

    @FXML
    private void handleUpdateButton() {

    }

    @FXML
    private void handleClosingButton() {
        Stage popupStage = (Stage) closeButton.getScene().getWindow();
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
