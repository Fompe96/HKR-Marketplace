package Controllers;

import Database.DBHandler;
import Models.Item;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class InsertItemController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private CheckBox excellentBox, veryGoodBox, goodBox, poorBox, vehiclesBox, petsBox, homeBox, electronicsBox, otherBox;
    @FXML
    private TextField nameField, priceField, uploadField;
    @FXML
    private AnchorPane root;
    @FXML
    private TextArea descriptionArea;
    private double x, y;
    private String selectedCondition = "Excellent";
    private String selectedCategory = "Vehicles";
    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uploadField.setEditable(false);
        Platform.runLater(() -> root.requestFocus());
    }
    @FXML
    private void handleConditionCheckboxes(ActionEvent event) {
        if (event.getSource() == excellentBox) {
            selectedCondition = excellentBox.getText();
            excellentBox.setSelected(true);
            veryGoodBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        } else if (event.getSource() == veryGoodBox) {
            selectedCondition = veryGoodBox.getText();
            veryGoodBox.setSelected(true);
            excellentBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        } else if (event.getSource() == goodBox) {
            selectedCondition = goodBox.getText();
            goodBox.setSelected(true);
            excellentBox.setSelected(false);
            veryGoodBox.setSelected(false);
            poorBox.setSelected(false);
        } else if (event.getSource() == poorBox) {
            selectedCondition = poorBox.getText();
            poorBox.setSelected(true);
            excellentBox.setSelected(false);
            veryGoodBox.setSelected(false);
            goodBox.setSelected(false);
        }
    }

    @FXML
    private void handleCategoryCheckboxes(ActionEvent event) {
        if (event.getSource() == vehiclesBox) {
            selectedCategory = vehiclesBox.getText();
            vehiclesBox.setSelected(true);
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        } else if (event.getSource() == petsBox) {
            selectedCategory = petsBox.getText();
            petsBox.setSelected(true);
            vehiclesBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        } else if (event.getSource() == homeBox) {
            selectedCategory = homeBox.getText();
            homeBox.setSelected(true);
            vehiclesBox.setSelected(false);
            petsBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        } else if (event.getSource() == electronicsBox) {
            selectedCategory = electronicsBox.getText();
            electronicsBox.setSelected(true);
            vehiclesBox.setSelected(false);
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            otherBox.setSelected(false);
        } else if (event.getSource() == otherBox) {
            selectedCategory = otherBox.getText();
            otherBox.setSelected(true);
            vehiclesBox.setSelected(false);
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
        }
    }

    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            uploadField.setText(imageFile.getPath());
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "File does not exist!").showAndWait();
        }
    }

    @FXML
    private void handleResetButton() {
        imageFile = null;
        uploadField.setText("");
    }

    @FXML
    private void handleInsertButton() {
        if (!nameField.getText().equals("") && !priceField.getText().equals("")) {
            if (!descriptionArea.getText().equals("") && descriptionArea.getText().length() > 10) {
                if (priceField.getText().matches("[0-9]+") && Double.parseDouble(priceField.getText()) >= 0 && Double.parseDouble(priceField.getText()) <= 1000000) {
                    if (imageFile != null) {
                        Item createdItem = new Item(0, nameField.getText(), Double.parseDouble(priceField.getText()), descriptionArea.getText(), selectedCondition, selectedCategory, imageFile, Singleton.getInstance().getLoggedInEmail(), true);
                        DBHandler.insertItemIntoDB(createdItem);
                        MessageHandler.getInformationAlert("Success", "Success", "The account was successfully added to the database!").showAndWait();
                        Singleton.getInstance().setLastInsertedObject("Item");
                        SceneChanger.changeScene("../Views/Administration.fxml"); // Might replace by transfering the added account to tableview later
                        handleClosingButton();
                    } else {
                        MessageHandler.getErrorAlert("Error", "Error", "You have to choose a picture of your item.").showAndWait();
                    }
                } else {
                    MessageHandler.getErrorAlert("Error", "Error", "Price has to be between 0 - 1.000.000 kr and only contain numbers.").showAndWait();
                }
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "Description needs to be atleast 10 letters long.").showAndWait();
            }
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "All information needs to be entered.").showAndWait();
        }

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
