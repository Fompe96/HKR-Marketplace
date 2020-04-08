package Controllers;

import Database.DBHandler;
import Models.Account;
import Models.Sale;
import Models.SceneChanger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    @FXML
    private Button editAccounts, editSales;
    private DBHandler dbHandler;
    private double x, y;
    private ObservableList<Account> accounts;
    private ObservableList<Sale> sales;

    @FXML private TableView<Account> accountsTableView;
    @FXML private TableColumn <Account, String> username;
    @FXML private TableColumn <Account, String> password;
    @FXML private TableColumn <Account, String> email;
    @FXML private TableColumn <Account, Boolean> admin;
    @FXML private TableColumn <Account, Blob> accpicture;

    @FXML private TableView<Sale> salesTableView;
    @FXML private TableColumn <Sale, Integer> id;
    @FXML private TableColumn <Sale, String> salesname;
    @FXML private TableColumn <Sale, Double> price;
    @FXML private TableColumn <Sale, String> description;
    @FXML private TableColumn <Sale, String> condition;
    @FXML private TableColumn <Sale, String> category;
    @FXML private TableColumn <Sale, Blob> picture;

    public void changeUsernameEvent(TableColumn.CellEditEvent editedCell) {
        /*
        Account selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
        selectedAccount.setUserName(editedCell.getNewValue().toString());

         */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        retrieveAccounts();
        retrieveSales();
        setupColumns();
        handleEditAccountsButton(); // Default selected choice
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

    private void retrieveAccounts() {    // Retrievees all accounts from DB and places them as objects in observable list accounts.
        accounts = FXCollections.observableArrayList();
        accounts = dbHandler.retrieveAllAccounts();
        for (Account account : accounts) {
            System.out.println(account);
        }

    }

    private void retrieveSales() {   // Retrieves all sales from the DB and places them as objects in observable list sales
        sales = FXCollections.observableArrayList();
        sales = dbHandler.retrieveAllSales();
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    private void setupColumns() {
        // Account Columns
        username.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
        username.setCellFactory(TextFieldTableCell.forTableColumn());   // Makes cells in column editable
        password.setCellValueFactory(new PropertyValueFactory<Account, String>("Password"));
        email.setCellValueFactory(new PropertyValueFactory<Account, String>("Email"));
        admin.setCellValueFactory(new PropertyValueFactory<Account, Boolean>("Admin"));
        accpicture.setCellValueFactory(new PropertyValueFactory<Account, Blob>("Picture"));
        accountsTableView.setItems(accounts);
        accountsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Allows for multiple rows to be selected
        // Sales Columns
        id.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("id"));
        salesname.setCellValueFactory(new PropertyValueFactory<Sale, String >("Name"));
        price.setCellValueFactory(new PropertyValueFactory<Sale, Double>("Price"));
        description.setCellValueFactory(new PropertyValueFactory<Sale, String>("Description"));
        condition.setCellValueFactory(new PropertyValueFactory<Sale, String>("Condition"));
        category.setCellValueFactory(new PropertyValueFactory<Sale, String>("Category"));
        picture.setCellValueFactory(new PropertyValueFactory<Sale, Blob>("Picture"));
        salesTableView.setItems(sales);
        salesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleRemoveButton() {

    }

    @FXML
    private void handleInsertButton() {

    }

    @FXML
    private void handleUpdateButton() {

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
