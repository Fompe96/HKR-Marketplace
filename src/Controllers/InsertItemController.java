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
    private Button closebutton;
    @FXML
    private CheckBox excellentbox, verygoodbox, goodbox, poorbox, vehiclesbox, petsbox, homebox, electronicsbox, otherbox;
    @FXML
    private TextField namefield, pricefield, uploadfield;
    @FXML
    private AnchorPane root;
    @FXML
    private TextArea descriptionarea;
    private double x, y;
    private String selectedCondition = "Excellent";
    private String selectedCategory = "Vehicles";
    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uploadfield.setEditable(false);
        Platform.runLater(() -> root.requestFocus());
    }
    @FXML
    private void handleConditionCheckboxes(ActionEvent event) {
        if (event.getSource() == excellentbox) {
            selectedCondition = excellentbox.getText();
            excellentbox.setSelected(true);
            verygoodbox.setSelected(false);
            goodbox.setSelected(false);
            poorbox.setSelected(false);
        } else if (event.getSource() == verygoodbox) {
            selectedCondition = verygoodbox.getText();
            verygoodbox.setSelected(true);
            excellentbox.setSelected(false);
            goodbox.setSelected(false);
            poorbox.setSelected(false);
        } else if (event.getSource() == goodbox) {
            selectedCondition = goodbox.getText();
            goodbox.setSelected(true);
            excellentbox.setSelected(false);
            verygoodbox.setSelected(false);
            poorbox.setSelected(false);
        } else if (event.getSource() == poorbox) {
            selectedCondition = poorbox.getText();
            poorbox.setSelected(true);
            excellentbox.setSelected(false);
            verygoodbox.setSelected(false);
            goodbox.setSelected(false);
        }
    }

    @FXML
    private void handleCategoryCheckboxes(ActionEvent event) {
        if (event.getSource() == vehiclesbox) {
            selectedCategory = vehiclesbox.getText();
            vehiclesbox.setSelected(true);
            petsbox.setSelected(false);
            homebox.setSelected(false);
            electronicsbox.setSelected(false);
            otherbox.setSelected(false);
        } else if (event.getSource() == petsbox) {
            selectedCategory = petsbox.getText();
            petsbox.setSelected(true);
            vehiclesbox.setSelected(false);
            homebox.setSelected(false);
            electronicsbox.setSelected(false);
            otherbox.setSelected(false);
        } else if (event.getSource() == homebox) {
            selectedCategory = homebox.getText();
            homebox.setSelected(true);
            vehiclesbox.setSelected(false);
            petsbox.setSelected(false);
            electronicsbox.setSelected(false);
            otherbox.setSelected(false);
        } else if (event.getSource() == electronicsbox) {
            selectedCategory = electronicsbox.getText();
            electronicsbox.setSelected(true);
            vehiclesbox.setSelected(false);
            petsbox.setSelected(false);
            homebox.setSelected(false);
            otherbox.setSelected(false);
        } else if (event.getSource() == otherbox) {
            selectedCategory = otherbox.getText();
            otherbox.setSelected(true);
            vehiclesbox.setSelected(false);
            petsbox.setSelected(false);
            homebox.setSelected(false);
            electronicsbox.setSelected(false);
        }
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

    @FXML
    private void handleResetButton() {
        imageFile = null;
        uploadfield.setText("");
    }

    @FXML
    private void handleInsertButton() {
        if (!namefield.getText().equals("") && !descriptionarea.getText().equals("") && !pricefield.getText().equals("")) {
            if (pricefield.getText().matches("[0-9]+") && Double.parseDouble(pricefield.getText()) > 29) {
                if (imageFile != null) {
                    Item createdItem = new Item(namefield.getText(), Double.parseDouble(pricefield.getText()), descriptionarea.getText(), selectedCondition, selectedCategory, imageFile, Singleton.getInstance().getLoggedInEmail());
                    DBHandler.insertItemIntoDB(createdItem);
                    MessageHandler.getInformationAlert("Success", "Success", "The account was successfully added to the database!").showAndWait();
                    Singleton.getInstance().setLastInsertedObject("Item");
                    SceneChanger.changeScene("../Views/Administration.fxml"); // Might replace by transfering the added account to tableview later
                    handleClosingButton();
                } else {
                    MessageHandler.getErrorAlert("Error", "Error", "You have to choose a picture of your item.").showAndWait();
                }
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "Price has to be atleast 30 kr and only contain numbers.").showAndWait();
            }
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "All information needs to be entered.").showAndWait();
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
