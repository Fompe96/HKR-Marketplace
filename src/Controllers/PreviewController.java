package Controllers;

import Database.DBHandler;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {

    @FXML
    private Label nameLabel, priceLabel, descriptionLabel, categoryLabel, conditionLabel;

    @FXML
    private ImageView image;

    File file = Singleton.getInstance().getItem().getImageFile();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image imageView = new Image(file.toURI().toString());
        image.setImage(imageView);
        nameLabel.setText(Singleton.getInstance().getItem().getName());
        priceLabel.setText(String.valueOf(Singleton.getInstance().getItem().getPrice()));
        descriptionLabel.setText(Singleton.getInstance().getItem().getDescription());
        categoryLabel.setText(Singleton.getInstance().getItem().getCategory());
        conditionLabel.setText(Singleton.getInstance().getItem().getCondition());

    }

    @FXML
    private void handleBackButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
    }

    @FXML
    private void addToSale() throws SQLException, FileNotFoundException {
        Connection dbConnection = DBHandler.getConnection();
        PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`product` (`name`, `price`, `description`, `condition`, `category`, `picture`, `email`) VALUES (?, ?, ?, ?, ?, ?, ?);\n");

        statement.setString(1, nameLabel.getText());
        statement.setDouble(2, Double.parseDouble(priceLabel.getText()));
        statement.setString(3, descriptionLabel.getText());
        statement.setString(4, categoryLabel.getText());
        statement.setString(5, conditionLabel.getText());
        FileInputStream fis = new FileInputStream(file);
        statement.setBinaryStream(6, fis, (int) file.length());
        statement.setString(7, Singleton.getInstance().getLoggedInEmail());

        statement.executeUpdate();
        DBHandler.closeConnection();

        MessageHandler.getInformationAlert("Success", "Information", "Congratulations! your product is now up for sale!").showAndWait();

        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Sell.fxml");
    }
}


