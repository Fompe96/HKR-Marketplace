package Controllers;

import Database.DBHandler;
import Models.MessageHandler;
import Models.SceneChanger;
import Models.Singleton;
import Models.ToolTipHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField userPassword;

    @FXML
    private TextField userEmail;

    @FXML
    private AnchorPane root;
    @FXML
    private Pane logoPane;
    @FXML
    private Pane loginInformationPane;

    @FXML
    private Button minimizeButton, closingButton;

    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition ft = new FadeTransition(Duration.millis(2000), logoPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
        FadeTransition ftSecond = new FadeTransition(Duration.millis(2000), loginInformationPane);
        ftSecond.setFromValue(0);
        ftSecond.setToValue(1);
        ftSecond.play();
        Platform.runLater(() -> root.requestFocus());
        handleToolTip();
    }

    private void handleToolTip() {
        ToolTipHandler.getToolTipCloseButton(closingButton);
        ToolTipHandler.getToolTipMinimizeButton(minimizeButton);
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
    private void logInButtonAction() {
        if (userEmail.getText().equals("") || userPassword.getText().equals("")) {
            MessageHandler.getErrorAlert("Error", "Error", "Please enter all fields").showAndWait();
        } else {
            DBHandler.getConnection();
            boolean checkIfExists = DBHandler.findUser(userEmail.getText(), userPassword.getText());
            if (checkIfExists) {
                Singleton.getInstance().setLoggedInAccount(DBHandler.getUserInformation(userEmail.getText()));
                SceneChanger.changeScene("../Views/Marketplace.fxml");
            } else {
                MessageHandler.getErrorAlert("Error", "Error", "Login credentials not found").showAndWait();
            }
            DBHandler.closeConnection();
        }
    }

    @FXML
    private void registerButtonAction() {
        SceneChanger.changeScene("../Views/SignUp.fxml");
    }

    @FXML
    private void retrieveCredentialsButtonAction() {
        SceneChanger.changeScene("../Views/RetrieveCredentials.fxml");
    }
}