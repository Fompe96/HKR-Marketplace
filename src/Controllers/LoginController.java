package Controllers;

import javafx.event.ActionEvent;

public class LoginController {
    SceneChanger sceneChanger = new SceneChanger();

    public void logIn() {

        //if (retrieveCredentials) kalla sceneChanger och byt scene.
    }

    public void register() {

    }

    public void retrieveCredentials() {
    }

    void loadMarket(ActionEvent event) {
        SceneChanger.changeScene(event, "Market.fxml", "HKR Marketplace");
    }
}
