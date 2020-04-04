package Controllers;

import Models.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MarketplaceController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(new Image("https://usercontent1.hubstatic.com/11310434_f520.jpg"));
        imageView1.setImage(new Image("https://i.imgur.com/UslMM9I.png"));
    }

    @FXML
    public void sellButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Sell.fxml", "Sell");
    }
}