package Controllers;

import Database.DBHandler;
import Models.*;
import javafx.collections.ObservableList;
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
import java.util.Objects;
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
    private Button closeButton, updateButton;
    @FXML
    private CheckBox conditionExcellent, conditionVeryGood, conditionGood, conditionPoor,
        categoryVehicles, categoryPets, categoryHome, categoryElectronics, categoryOther,
        activeTrue, activeFalse;

    private String selectedCondition;
    private String selectedCategory;
    private boolean newActiveStatus;
    private double x,y;
    private Item itemToEdit;
    private Item newItem;
    private File newImage;
    private boolean changeDone; // Keeps track if any new information has been entered

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemToEdit = (Item) Singleton.getInstance().getObjectToEdit();
        displayOldItem();
        displayNewItemFields();
        //updateButton.setDisable(true);
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

    // Sets the correct condition checkbox depending on item to be edited.
    private void setNewConditionBox() {
        String condition = itemToEdit.getCondition();
        selectedCondition = itemToEdit.getCondition();
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
    // Sets the correct category checkbox depending on item to be edited.
    private void setNewCategoryBox() {
        String category = itemToEdit.getCategory();
        selectedCategory = itemToEdit.getCategory();
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
    // Sets the correct active checkbox depending on item to be edited.
    private void setNewActiveBox() {
        newActiveStatus = itemToEdit.isSaleActive();
        if (itemToEdit.isSaleActive()) {
            activeTrue.setSelected(true);
        } else if (!itemToEdit.isSaleActive()) {
            activeFalse.setSelected(true);
        }
    }
    // Method that checks and unchecks all category boxes appropriately depending no which is clicked.
    @FXML
    private void handleActiveCheckboxes(ActionEvent event) {
        if (event.getSource() == activeTrue) {
            newActiveStatus = true;
            activeTrue.setSelected(true);
            activeFalse.setSelected(false);
        } else if (event.getSource() == activeFalse) {
            newActiveStatus = false;
            activeFalse.setSelected(true);
            activeTrue.setSelected(false);
        }
    }
    // Allows user to upload an image associated with the item
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
    // Resets the currently selected picture
    @FXML
    private void handleResetButton() {
        newImage = null;
        itemUploadField.setText("Null");
    }
    // Methods that calls validation methods and if everything is in order for an update updates the selected item in DB
    @FXML
    private void handleUpdateButton() {
        newItem = new Item(itemToEdit.getId(), itemToEdit.getName(), itemToEdit.getPrice(), itemToEdit.getDescription(), itemToEdit.getCondition(), itemToEdit.getCategory(), itemToEdit.getImageFile(), itemToEdit.getOwner(), itemToEdit.isSaleActive());
        boolean validationChecker = true;
        changeDone = false;
        if (!newItemName.getText().equals(oldItemName.getText())) {
            validationChecker = validateNewName();
        }
        if (validationChecker && !newItemPrice.getText().equals(oldItemPrice.getText())) {
            validationChecker = validateNewPrice();
        }
        if (validationChecker && !newItemDescription.getText().equals(oldItemDescription.getText())) {
            validationChecker = validateNewDescription();
        }
        if (validationChecker && !selectedCondition.equals(oldItemCondition.getText())) {
            newItem.setCondition(selectedCondition);
            changeDone = true;
        }
        if (validationChecker && !selectedCategory.equals(oldItemCategory.getText())) {
            newItem.setCategory(selectedCategory);
            changeDone = true;
        }
        if (validationChecker && !newItemOwner.getText().equals(oldItemOwner.getText())) {
            validationChecker = validateNewOwner();
        }
        if (validationChecker && !Objects.equals(itemToEdit.getImageFile(), newImage)) {
            validationChecker = validateNewImage();
        }
        if (validationChecker && !Objects.equals(itemToEdit.isSaleActive(), newActiveStatus)) {
            newItem.setSaleActive(newActiveStatus);
            changeDone = true;
        }
        if (validationChecker) {
            if (changeDone) {
                newItem.setName(newItemName.getText());
                newItem.setPrice(Double.parseDouble(newItemPrice.getText()));
                newItem.setDescription(newItemDescription.getText());
                newItem.setOwner(newItemOwner.getText());
                newItem.setImageFile(newImage);
                DBHandler.updateItemInformation(newItem);
                Singleton.getInstance().setLastInsertedObject("Item");
                SceneChanger.changeScene("../Views/Administration.fxml");
                handleClosingButton();
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "No changes have been made to the item.").showAndWait();
            }
        }
    }
    // Validates the newName value in the textfield
    private boolean validateNewName() {
        if (!newItemName.getText().equals("") && newItemName.getText().length() > 2) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "New name has to be atleast 3 letters").showAndWait();
            return false;
        }
    }
    // Validates that the new price is legit
    private boolean validateNewPrice() {
        if (newItemPrice.getText().matches("[0-9]+") && Double.parseDouble(newItemPrice.getText()) >= 0 && Double.parseDouble(newItemPrice.getText()) <= 1000000) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "Price has to be between 0 - 1.000.000 and only contain numbers.").showAndWait();
            return false;
        }

    }
    // Validates new description
    private boolean validateNewDescription() {
        if (!newItemDescription.getText().equals("") && newItemDescription.getText().length() > 10) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "Description needs to be atleast 10 letters long.").showAndWait();
            return false;
        }
    }
    // Validates that the new owner of the item actually exists in the database.
    private boolean validateNewOwner() {
        ObservableList<Account> registeredAccounts = Singleton.getInstance().getAccounts();
        for (Account account : registeredAccounts) {
            if (account.getEmail().equalsIgnoreCase(newItemOwner.getText())) {
                changeDone = true;
                return true;
            }
        }
        MessageHandler.getErrorAlert("Error", "Error", "Entered owner doesn't exist.").showAndWait();
        return false;
    }
    // validates the new image
    private boolean validateNewImage() {
        if (!Objects.equals(itemToEdit.getImageFile(), newImage)) {
            changeDone = true;
            return true;
        } else {
            MessageHandler.getErrorAlert("Error", "Error", "Something went wrong with picture.").showAndWait();
            return false;
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
