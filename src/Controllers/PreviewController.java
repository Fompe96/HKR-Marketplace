package Controllers;

import Models.SceneChanger;
import Models.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {

    @FXML
    private Label label1, label2, label3, label4, label5;


    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File(Singleton.getInstance().getItem().getPicturePath());
        Image imageView = new Image(file.toURI().toString());
        image.setImage(imageView);
        label1.setText(Singleton.getInstance().getItem().getName());
        label2.setText(String.valueOf(Singleton.getInstance().getItem().getPrice()));
        label3.setText(Singleton.getInstance().getItem().getDescription());
        label4.setText(Singleton.getInstance().getItem().getCategory());
        label5.setText(Singleton.getInstance().getItem().getCondition());

    }


    @FXML
    private void handleBackButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
    }
}
