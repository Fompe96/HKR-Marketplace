package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SellController implements Initializable {

    @FXML
    private TextField nameOfProductTextField;

    @FXML
    private TextField priceOfProductTextField;

    @FXML
    private TextArea descriptionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleBackButton(ActionEvent event){
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
    }

    @FXML
    private void handleAddSaleButton(ActionEvent event){

    }
}



