package Controllers;

import Models.Item;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {

    private double x, y;

    ArrayList<Item> itemArrayList = new ArrayList<>();

    @FXML
    private Label label1, label2, label3;

    @FXML
    private TextField textField1;

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
        Item item = new Item();
        item.setName(label1.getText());
        item.setPrice(Double.parseDouble(label2.getText()));
        item.setDescription(label3.getText());
        itemArrayList.add(item);
       // Singleton.getInstance().setItemArrayList(itemArrayList);
        SceneChanger.changeScene("../Views/Sell.fxml");
    }
}
