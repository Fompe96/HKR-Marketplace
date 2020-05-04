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

public class ItemViewController implements Initializable {

    @FXML
    private Label nameLabel, priceLabel, descriptionLabel, categoryLabel, conditionLabel;

    @FXML
    private ImageView image;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        image.setImage(Singleton.getInstance().getItem().getImage());
        nameLabel.setText(Singleton.getInstance().getItem().getName());
        priceLabel.setText(String.valueOf(Singleton.getInstance().getItem().getPrice()));
        descriptionLabel.setText(Singleton.getInstance().getItem().getDescription());
        categoryLabel.setText(Singleton.getInstance().getItem().getCategory());
        conditionLabel.setText(Singleton.getInstance().getItem().getCondition());

    }

    @FXML
    private void handleBackButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }


    }


