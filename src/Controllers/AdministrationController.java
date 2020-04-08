package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import com.mysql.cj.jdbc.Blob;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    @FXML
    private Button editAccounts, editSales;
    @FXML
    private TableView accountsTableView, salesTableView;
    private DBHandler dbHandler;
    private double x, y;
    private ObservableList<ObservableList> data;

    @FXML
    private TableColumn id, salesname, price, description, condition, category, picture;    // Columns in salesTableView
    @FXML
    private TableColumn username, password, email, admin, accpicture;   // Columns in accountTableView

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        handleEditAccountsButton();
    }

    @FXML
    private void handleEditAccountsButton() {
        selectEditAccounts();
    }

    @FXML
    private void handleEditSalesButton() {
        selectEditSales();
    }

    private void selectEditAccounts() { // Handles all visual changes when editAccounts is pressed.
        editSales.setStyle("-fx-background-color:  #4f4d4d;");
        editAccounts.setStyle("-fx-background-color:  #46ab57;");
        accountsTableView.setVisible(true);
        salesTableView.setVisible(false);
    }

    private void selectEditSales() { // Handles all visual changes when editSales is pressed.
        editAccounts.setStyle("-fx-background-color:  #4f4d4d;");
        editSales.setStyle("-fx-background-color:  #46ab57;");
        salesTableView.setVisible(true);
        accountsTableView.setVisible(false);
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
