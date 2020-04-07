package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    private DBHandler dbHandler;
    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
    }

    @FXML
    private void handleSellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml", "Sell");
    }

    @FXML
    private void handleMarketPlaceButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml", "HKR Marketplace");
    }

    @FXML
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml", "Sell");
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
}
