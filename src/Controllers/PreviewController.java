package Controllers;

import Models.Item;
import Models.SceneChanger;
import Models.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {


    ArrayList<Item> itemArrayList = new ArrayList<>();

    @FXML
    private Label label1, label2, label3;


    @FXML
    private ImageView image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File(Singleton.getInstance().getImage());
        Image imageView = new Image(file.toURI().toString());
        image.setImage(imageView);
        label1.setText(Singleton.getInstance().getItemName());
        label2.setText(Singleton.getInstance().getPriceOfProduct().toString());
        label3.setText(Singleton.getInstance().getDescription());
    }


    @FXML
    private void handleBackButton() {
        Item item = new Item();
        item.setName(label1.getText());
        item.setPrice(Double.parseDouble(label2.getText()));
        item.setDescription(label3.getText());
        itemArrayList.add(item);
       // Singleton.getInstance().setItemArrayList(itemArrayList);
        SceneChanger.changeScene("../Views/Sell.fxml");
    }
}
