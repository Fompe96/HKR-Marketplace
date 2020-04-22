package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MarketplaceController implements Initializable {

    private double x, y;
    @FXML
    private ImageView imageView, imageView1, adminview;

    @FXML
    private Button adminButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(new Image("https://usercontent1.hubstatic.com/11310434_f520.jpg"));
        imageView1.setImage(new Image("https://www.kattkompaniet.nu/images/5.63.1606161417/kattleksaker-fatcat.jpeg"));
        System.out.println("The user who just logged in is: " + Singleton.getInstance().getLoggedInAccount()); // This is here for testing purposes!

        if (Singleton.getInstance().getLoggedInAccount().isAdmin()) {
            adminview.setVisible(true);
            adminButton.setDisable(false);
        }
    }
    @FXML
    private void handleLogOutButton(){
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    public void sellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
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
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml");
    }

    private void addToFavourite(){

    }
}
