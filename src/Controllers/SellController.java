package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SellController implements Initializable {

    @FXML
    private TextField nameOfProductTextField;

    @FXML
    private TextField priceOfProductTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private CheckBox excellentBox;

    @FXML
    private CheckBox veryGoodBox;

    @FXML
    private CheckBox goodBox;

    @FXML
    private CheckBox poorBox;

    private int idProduct;

    DBHandler dbHandler = new DBHandler();
    private Connection dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


    double x, y;

    @FXML
    private void handleClosingButton(ActionEvent event) {
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
    private void handleBackButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
    }

    @FXML
    private void handleSettingsButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Settings.fxml", "Sell");
    }

    @FXML
    private void handleAddSaleButton() {
        if (nameOfProductTextField.getText().equals("") || priceOfProductTextField.getText().equals("") || descriptionTextArea.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please Enter your information in all fields");
            alert.showAndWait();
        } else {
            dbConnection = dbHandler.getConnection();
            try {
                PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`product` (`idProduct`, `name`, `price`, `description`) VALUES (?, ?, ?, ?);\n");
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
                System.out.println(e);
            }
        }
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}



