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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellController implements Initializable {


    @FXML
    private TextField nameOfProductTextField, priceOfProductTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private CheckBox excellentBox, veryGoodBox, goodBox, poorBox, vehiclesBox, petsBox, homeBox, electronicsBox, otherBox;

    @FXML
    private Button adminButton, uploadImageButton;

    @FXML
    private TextField filePathTextField;

    @FXML
    private ImageView adminview;

    ArrayList<Item> itemArrayList = new ArrayList<>();

    private int idProduct;

    private File file;

    private FileInputStream fis;

    private DBHandler dbHandler;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        if (Singleton.getInstance().getLoggedInAccount().isAdmin()) {
            adminview.setVisible(true);
            adminButton.setDisable(false);
        }

        itemArrayList = Singleton.getInstance().getItemArrayList();

        for (int i = 0; i < itemArrayList.size(); i++) {
            nameOfProductTextField.setText(itemArrayList.get(i).getName());
            priceOfProductTextField.setText(String.valueOf(itemArrayList.get(i).getPrice()));
            descriptionTextArea.setText(itemArrayList.get(i).getDescription());
        }
    }

    @FXML
    private void handleLogOutButton(){
        SceneChanger.changeScene("../Views/Login.fxml", "Login");
    }

    @FXML
    private void handleUploadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if(file != null){
            filePathTextField.setText(file.getPath());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("File does not Exist!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleVehiclesBox(){
        if(vehiclesBox.isSelected()){
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handlePetsBox(){
        if(petsBox.isSelected()){
            vehiclesBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleHomeBox(){
        if(homeBox.isSelected()){
            petsBox.setSelected(false);
            vehiclesBox.setSelected(false);
            electronicsBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleElectronicsBox(){
        if(electronicsBox.isSelected()){
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            vehiclesBox.setSelected(false);
            otherBox.setSelected(false);
        }
    }

    @FXML
    private void handleOtherBox(){
        if(otherBox.isSelected()){
            petsBox.setSelected(false);
            homeBox.setSelected(false);
            electronicsBox.setSelected(false);
            vehiclesBox.setSelected(false);
        }
    }

    @FXML
    private void handleExcellentBox(){
        if(excellentBox.isSelected()){
            veryGoodBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handleVeryGoodBox(){
        if(veryGoodBox.isSelected()){
            excellentBox.setSelected(false);
            goodBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handleGoodBox(){
        if(goodBox.isSelected()){
            veryGoodBox.setSelected(false);
            excellentBox.setSelected(false);
            poorBox.setSelected(false);
        }
    }

    @FXML
    private void handlePoorBox(){
        if(poorBox.isSelected()){
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
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
    }

    @FXML
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml", "Sell");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml", "Administration Page");
    }

    @FXML
    private void handleAddSaleButton() {
        if (nameOfProductTextField.getText().equals("") || priceOfProductTextField.getText().equals("") || descriptionTextArea.getText().equals("") || filePathTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please Enter your information in all fields");
            alert.showAndWait();
        } else {
            Connection dbConnection = dbHandler.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`product` (`idProduct`, `name`, `price`, `description`, `condition`, `category`, `picture`) VALUES (?, ?, ?, ?, ?, ?, ?);\n");
                PreparedStatement count = dbConnection.prepareStatement("SELECT count(idProduct) FROM product;");
                ResultSet rs = count.executeQuery();
                while (rs.next()) {
                    idProduct = rs.getInt(1);
                    setIdProduct(idProduct);
                }
                statement.setInt(1, getIdProduct() + 1);
                statement.setString(2, nameOfProductTextField.getText());
                statement.setDouble(3, Double.parseDouble(priceOfProductTextField.getText()));
                statement.setString(4, descriptionTextArea.getText());
                if (excellentBox.isSelected()) {
                    statement.setString(5,excellentBox.getText());
                } else if (veryGoodBox.isSelected()) {
                    statement.setString(5, veryGoodBox.getText());
                } else if (goodBox.isSelected()){
                    statement.setString(5, goodBox.getText());
                } else if (poorBox.isSelected()){
                    statement.setString(5, poorBox.getText());
                }

                if (vehiclesBox.isSelected()){
                    statement.setString(6, vehiclesBox.getText());
                } else if (petsBox.isSelected()){
                    statement.setString(6, petsBox.getText());
                } else if (homeBox.isSelected()){
                    statement.setString(6, homeBox.getText());
                } else if (electronicsBox.isSelected()){
                    statement.setString(6, electronicsBox.getText());
                } else if (otherBox.isSelected()){
                    statement.setString(6, otherBox.getText());
                }

                fis = new FileInputStream(file);
                statement.setBinaryStream(7, fis, (int)file.length());

                statement.executeUpdate();
                dbHandler.closeConnection();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Congratulations! your product is now up for sale!");
                alert.showAndWait();

                nameOfProductTextField.setText("");
                priceOfProductTextField.setText("");
                descriptionTextArea.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handlePreviewButton(){
        if (nameOfProductTextField.getText().equals("") || priceOfProductTextField.getText().equals("") || descriptionTextArea.getText().equals("")){
            MessageHandler.getErrorAlert("ERROR","Missing input", "Make sure you entered everything correctly").showAndWait();
        } else {
            Item item = new Item();
            item.setName(nameOfProductTextField.getText());
            item.setPrice(Double.parseDouble(priceOfProductTextField.getText()));
            item.setDescription(descriptionTextArea.getText());
            itemArrayList.add(item);
            Singleton.getInstance().setItemArrayList(itemArrayList);
            SceneChanger.changeScene("../Views/Preview.fxml", "Preview");
        }
    }

    private int getIdProduct() {
        return idProduct;
    }

    private void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public TextField getNameOfProductTextField() {
        return nameOfProductTextField;
    }

    public TextField getPriceOfProductTextField() {
        return priceOfProductTextField;
    }

    public TextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }
}



