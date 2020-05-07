package Controllers;

import Database.DBHandler;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    private double x, y;


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
    public void goBack() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
        Singleton.getInstance().setItem(null);
    }

    public void contactSeller(){
        SceneChanger.changeScene("../Views/ContactSeller.fxml");
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
    private void addProductToFavorite() throws FileNotFoundException, SQLException {
        String userEmail = Singleton.getInstance().getLoggedInEmail();
        int productId = Singleton.getInstance().getItem().getId();
        DBHandler.addProductToFavorites(userEmail, productId);
        MessageHandler.getConfirmationAlert("Success", "Success", "Product added to favorites").showAndWait();
    }


}


