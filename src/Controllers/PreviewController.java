package Controllers;

import Models.SceneChanger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleBackButton(){
        SceneChanger.changeScene("../Views/Sell.fxml", "Sell");
    }
}
