package Controllers;

import Database.DBConfig;
import Database.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    private int idProduct;

    DBHandler dbHandler = new DBHandler();
    private Connection dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
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



