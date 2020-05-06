package Controllers;

import Database.DBHandler;
import Models.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;


public class SellController implements Initializable {
    private final Item item = new Item();

    @FXML
    private TextField nameOfProductTextField, priceOfProductTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private CheckBox excellentBox, veryGoodBox, goodBox, poorBox, vehiclesBox, petsBox, homeBox, electronicsBox, otherBox;

    @FXML
    private Button adminButton, addSaleButton, previewButton, marketPlaceButton, settingsButton, closingButton, minimizeButton, uploadImageButton, logOutButton;

    @FXML
    private TextField filePathTextField;

    @FXML
    private ImageView adminview;

    private File file;

    private FileInputStream fis;

    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleAdminView();
        handleItemInformation();
        handleTooltip();
        handleCursorButtons();
    }

    private void handleCursorButtons() {
        adminButton.setCursor(Cursor.HAND);
        settingsButton.setCursor(Cursor.HAND);
        marketPlaceButton.setCursor(Cursor.HAND);
        previewButton.setCursor(Cursor.HAND);
        addSaleButton.setCursor(Cursor.HAND);
        uploadImageButton.setCursor(Cursor.HAND);
        logOutButton.setCursor(Cursor.HAND);
    }

    private void handleAdminView() {
        if (Singleton.getInstance().getLoggedInAccount().isAdmin()) {
            adminview.setVisible(true);
            adminButton.setDisable(false);
        }
    }

    @FXML
    private void handleMonitoringButton() {
        SceneChanger.changeScene("../Views/MonitoringSales.fxml");
    }

    private void handleItemInformation() {
        if (Singleton.getInstance().getItem() != null) {
            nameOfProductTextField.setText(Singleton.getInstance().getItem().getName());
            priceOfProductTextField.setText(String.valueOf(Singleton.getInstance().getItem().getPrice()));
            descriptionTextArea.setText(Singleton.getInstance().getItem().getDescription());
            filePathTextField.setText(Singleton.getInstance().getItem().getImageFile().getPath());
            file = Singleton.getInstance().getItem().getImageFile();
            if (Singleton.getInstance().getItem().getCategory().equals(vehiclesBox.getText())) {
                vehiclesBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCategory().equals(petsBox.getText())) {
                petsBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCategory().equals(homeBox.getText())) {
                homeBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCategory().equals(electronicsBox.getText())) {
                electronicsBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCategory().equals(otherBox.getText())) {
                otherBox.setSelected(true);
            }
            if (Singleton.getInstance().getItem().getCondition().equals(excellentBox.getText())) {
                excellentBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCondition().equals(veryGoodBox.getText())) {
                veryGoodBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCondition().equals(goodBox.getText())) {
                goodBox.setSelected(true);
            } else if (Singleton.getInstance().getItem().getCondition().equals(poorBox.getText())) {
                poorBox.setSelected(true);
            }
        }
    }

    private void handleTooltip() {
        ToolTipHandler.getToolTipSale(addSaleButton);
        ToolTipHandler.getToolTipPreview(previewButton);
        ToolTipHandler.getTooltipMarketPlace(marketPlaceButton);
        ToolTipHandler.getToolTipSettings(settingsButton);
        ToolTipHandler.getToolTipAdmin(adminButton);
        ToolTipHandler.getToolTipCloseButton(closingButton);
        ToolTipHandler.getToolTipMinimizeButton(minimizeButton);
    }

    @FXML
    private void handleLogOutButton() {
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathTextField.setText(file.getPath());
        } else {
            MessageHandler.getInformationAlert("Error", "Error", "File does not Exist!").showAndWait();
        }
    }

    @FXML
    private void handleVehiclesBox() {
        if (vehiclesBox.isSelected()) {
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handlePetsBox() {
        if (petsBox.isSelected()) {
            vehiclesBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleHomeBox() {
        if (homeBox.isSelected()) {
            petsBox.setSelected(false);
            vehiclesBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleElectronicsBox() {
        if (electronicsBox.isSelected()) {
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            vehiclesBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleOtherBox() {
        if (otherBox.isSelected()) {
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            vehiclesBox.setSelected(false);
        }
    }

    @FXML
    private void handleExcellentBox() {
        if (excellentBox.isSelected()) {
            veryGoodBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handleVeryGoodBox() {
        if (veryGoodBox.isSelected()) {
            excellentBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handleGoodBox() {
        if (goodBox.isSelected()) {
            veryGoodBox.setSelected(false);
            excellentBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handlePoorBox() {
        if (poorBox.isSelected()) {
            veryGoodBox.setSelected(false);
            goodBox.setSelected(false);
            excellentBox.setSelected(false);
        }
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
    private void handleBackButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }

    @FXML
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml");
    }

    @FXML
    private void handleAddSaleButton() {
        if (nameOfProductTextField.getText().equals("") || priceOfProductTextField.getText().equals("") || descriptionTextArea.getText().equals("") || filePathTextField.getText().equals("")) {
            MessageHandler.getErrorAlert("Error", "Error", "Please Enter your information in all fields").showAndWait();
        } else {
            Connection dbConnection = DBHandler.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`product` (`name`, `price`, `description`, `condition`, `category`, `picture`, `email`) VALUES (?, ?, ?, ?, ?, ?, ?);\n");

                statement.setString(1, nameOfProductTextField.getText());
                statement.setDouble(2, Double.parseDouble(priceOfProductTextField.getText()));
                statement.setString(3, descriptionTextArea.getText());
                if (excellentBox.isSelected()) {
                    statement.setString(4, excellentBox.getText());
                } else if (veryGoodBox.isSelected()) {
                    statement.setString(4, veryGoodBox.getText());
                } else if (goodBox.isSelected()) {
                    statement.setString(4, goodBox.getText());
                } else if (poorBox.isSelected()) {
                    statement.setString(4, poorBox.getText());
                }

                if (vehiclesBox.isSelected()) {
                    statement.setString(5, vehiclesBox.getText());
                } else if (petsBox.isSelected()) {
                    statement.setString(5, petsBox.getText());
                } else if (homeBox.isSelected()) {
                    statement.setString(5, homeBox.getText());
                } else if (electronicsBox.isSelected()) {
                    statement.setString(5, electronicsBox.getText());
                } else if (otherBox.isSelected()) {
                    statement.setString(5, otherBox.getText());
                }

                fis = new FileInputStream(file);
                statement.setBinaryStream(6, fis, (int) file.length());

                statement.setString(7, Singleton.getInstance().getLoggedInEmail());

                statement.executeUpdate();
                DBHandler.closeConnection();


                MessageHandler.getInformationAlert("Success", "Information", "Congratulations! your product is now up for sale!").showAndWait();

                handleResetFields();
                Singleton.getInstance().setItem(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleResetFields() {
        nameOfProductTextField.setText("");
        priceOfProductTextField.setText("");
        descriptionTextArea.setText("");
        petsBox.setSelected(false);
        vehiclesBox.setSelected(false);
        electronicsBox.setSelected(false);
        otherBox.setSelected(false);
        homeBox.setSelected(false);
        veryGoodBox.setSelected(false);
        goodBox.setSelected(false);
        poorBox.setSelected(false);
        excellentBox.setSelected(false);
        filePathTextField.setText("");
    }

    @FXML
    private void handlePreviewButton() {
        if (nameOfProductTextField.getText().equals("") || priceOfProductTextField.getText().equals("") || descriptionTextArea.getText().equals("") || filePathTextField.getText().equals("")) {
            MessageHandler.getErrorAlert("ERROR", "Missing input", "Make sure you entered everything correctly").showAndWait();
        }
        if (!priceOfProductTextField.getText().matches("[0-9.,]+")) {
            MessageHandler.getErrorAlert("Error", "Attention!", "Price is not valid").showAndWait();
        } else {
            item.setName(nameOfProductTextField.getText());
            item.setPrice(Double.parseDouble(priceOfProductTextField.getText()));
            item.setDescription(descriptionTextArea.getText());
            if (excellentBox.isSelected()) {
                item.setCondition(excellentBox.getText());
            } else if (veryGoodBox.isSelected()) {
                item.setCondition(veryGoodBox.getText());
            } else if (goodBox.isSelected()) {
                item.setCondition(goodBox.getText());
            } else if (poorBox.isSelected()) {
                item.setCondition(poorBox.getText());
            }

            if (vehiclesBox.isSelected()) {
                item.setCategory(vehiclesBox.getText());
            } else if (petsBox.isSelected()) {
                item.setCategory(petsBox.getText());
            } else if (homeBox.isSelected()) {
                item.setCategory(homeBox.getText());
            } else if (electronicsBox.isSelected()) {
                item.setCategory(electronicsBox.getText());
            } else if (otherBox.isSelected()) {
                item.setCategory(otherBox.getText());
            }

            item.setImageFile(file);

            Singleton.getInstance().setItem(item);
            SceneChanger.changeScene("../Views/Preview.fxml");
        }
    }

}



