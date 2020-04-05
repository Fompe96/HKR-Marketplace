package Controllers;

import Models.SceneChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SettingsController {


    double x, y;

    @FXML
    private void handleClosingButton(ActionEvent event) {
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
    private void handleMarketButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
    }

    @FXML
    public void sellButton(ActionEvent event) {
        SceneChanger.changeScene("../Views/Sell.fxml", "Sell");
    }
}
