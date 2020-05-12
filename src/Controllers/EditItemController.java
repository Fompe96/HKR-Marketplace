package Controllers;

import Models.Item;
import Models.MessageHandler;
import Models.Singleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private TextArea oldItemDescription, newItemDescription;
    @FXML
    private TextField oldItemName, oldItemPrice, oldItemCondition, oldItemCategory, oldItemOwner, oldItemActive,
            newItemName, newItemPrice, newItemOwner, itemUploadField;
    @FXML
    private ImageView oldItemImageView;
    @FXML
    private Button closeButton;
    @FXML
    private CheckBox conditionExcellent, conditionVeryGood, conditionGood, conditionPoor,
        categoryVehicles, categoryPets, categoryHome, categoryElectronics, categoryOther,
        activeTrue, activeFalse;
    private String selectedCondition;
    private String selectedCategory;
    private boolean active;
    private double x,y;
    private Item itemToEdit;
    private Item newItem;
    private File newImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemToEdit = (Item) Singleton.getInstance().getObjectToEdit();
        displayOldItem();
        displayNewItemFields();

    }
    // Displays the old values of the item to be edited
    private void displayOldItem() {
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
    // Fills the new fields with the old information when loaded so that the item can be edited faster
    private void displayNewItemFields() {
        newImage = itemToEdit.getImageFile();
        newItemName.setText(oldItemName.getText());
        newItemPrice.setText(oldItemPrice.getText());
        newItemDescription.setText(oldItemDescription.getText());
        setNewConditionBox();
        setNewCategoryBox();
        newItemOwner.setText(oldItemOwner.getText());
        setNewActiveBox();
        if (newImage != null) {
            itemUploadField.setText(newImage.getPath());
        } else {
            itemUploadField.setText("Null");
        }
    }

    // Checks the condition of the item to edit and checks the correct checkBox
    private void setNewConditionBox() {
        String condition = itemToEdit.getCondition();
        if (condition.equals("Excellent")) {
            conditionExcellent.setSelected(true);
        } else if (condition.equals("Very Good")) {
            conditionVeryGood.setSelected(true);
        } else if (condition.equals("Good")) {
            conditionGood.setSelected(true);
        } else if (condition.equals("Poor")) {
            conditionPoor.setSelected(true);
        }
    }
    // Method that checks and unchecks all condition boxes appropriately depending no which is clicked.
    @FXML
    private void handleConditionCheckboxes(ActionEvent event) {
        if (event.getSource() == conditionExcellent) {
            selectedCondition = conditionExcellent.getText();
            conditionExcellent.setSelected(true);
            conditionVeryGood.setSelected(false);
            conditionGood.setSelected(false);
            conditionPoor.setSelected(false);
        } else if (event.getSource() == conditionVeryGood) {
            selectedCondition = conditionVeryGood.getText();
            conditionVeryGood.setSelected(true);
            conditionExcellent.setSelected(false);
            conditionGood.setSelected(false);
            conditionPoor.setSelected(false);
        } else if (event.getSource() == conditionGood) {
            selectedCondition = conditionGood.getText();
            conditionGood.setSelected(true);
            conditionExcellent.setSelected(false);
            conditionVeryGood.setSelected(false);
            conditionPoor.setSelected(false);
        } else if (event.getSource() == conditionPoor) {
            selectedCondition = conditionPoor.getText();
            conditionPoor.setSelected(true);
            conditionExcellent.setSelected(false);
            conditionVeryGood.setSelected(false);
            conditionGood.setSelected(false);
        }
    }

    private void setNewCategoryBox() {
        String category = itemToEdit.getCategory();
        if (category.equals("Vehicles")) {
            categoryVehicles.setSelected(true);
        } else if (category.equals("Pets")) {
            categoryPets.setSelected(true);
        } else if (category.equals("Home")) {
            categoryHome.setSelected(true);
        } else if (category.equals("Electronics")) {
            categoryElectronics.setSelected(true);
        } else if (category.equals("Other")) {
            categoryOther.setSelected(true);
        }
    }

    // Method that checks and unchecks all category boxes appropriately depending no which is clicked.
    @FXML
    private void handleCategoryCheckboxes(ActionEvent event) {
        if (event.getSource() == categoryVehicles) {
            selectedCategory = categoryVehicles.getText();
            categoryVehicles.setSelected(true);
            categoryPets.setSelected(false);
            categoryHome.setSelected(false);
            categoryElectronics.setSelected(false);
            categoryOther.setSelected(false);
        } else if (event.getSource() == categoryPets) {
            selectedCategory = categoryPets.getText();
            categoryPets.setSelected(true);
            categoryVehicles.setSelected(false);
            categoryHome.setSelected(false);
            categoryElectronics.setSelected(false);
            categoryOther.setSelected(false);
        } else if (event.getSource() == categoryHome) {
            selectedCategory = categoryHome.getText();
            categoryHome.setSelected(true);
            categoryVehicles.setSelected(false);
            categoryPets.setSelected(false);
            categoryElectronics.setSelected(false);
            categoryOther.setSelected(false);
        } else if (event.getSource() == categoryElectronics) {
            selectedCategory = categoryElectronics.getText();
            categoryElectronics.setSelected(true);
            categoryVehicles.setSelected(false);
            categoryPets.setSelected(false);
            categoryHome.setSelected(false);
            categoryOther.setSelected(false);
        } else if (event.getSource() == categoryOther) {
            selectedCategory = categoryOther.getText();
            categoryOther.setSelected(true);
            categoryVehicles.setSelected(false);
            categoryPets.setSelected(false);
            categoryHome.setSelected(false);
            categoryElectronics.setSelected(false);
        }
    }

    private void setNewActiveBox() {
        if (itemToEdit.isSaleActive()) {
            activeTrue.setSelected(true);
        } else if (!itemToEdit.isSaleActive()) {
            activeFalse.setSelected(true);
        }
    }

    @FXML
    private void handleActiveCheckboxes(ActionEvent event) {
        if (event.getSource() == activeTrue) {
            active = true;
            activeTrue.setSelected(true);
            activeFalse.setSelected(false);
        } else if (event.getSource() == activeFalse) {
            active = false;
            activeFalse.setSelected(true);
            activeTrue.setSelected(false);
        }
    }

    @FXML
    private void handleUploadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        newImage = fileChooser.showOpenDialog(null);
        if (newImage != null) {
            itemUploadField.setText(newImage.getPath());
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "File does not exist!").showAndWait();
        }
    }

    @FXML
    private void handleResetButton() {
        newImage = null;
        itemUploadField.setText("Null");
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
