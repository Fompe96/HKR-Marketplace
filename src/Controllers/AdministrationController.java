package Controllers;

import Database.DBHandler;
import Models.Account;
import Models.MessageHandler;
import Models.Item;
import Models.SceneChanger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    @FXML
    private Button editAccounts, editItems;
    @FXML
    private HBox accountInputs;
    @FXML
    private TextField usernamefield, passwordfield, emailfield, adminfield, picturefield;
    private DBHandler dbHandler;
    private double x, y;
    private ObservableList<Account> accounts;
    private ObservableList<Item> items;
    private boolean accountsTableActive;

    @FXML
    private TableView<Account> accountsTableView;
    @FXML
    private TableColumn<Account, String> username;
    @FXML
    private TableColumn<Account, String> password;
    @FXML
    private TableColumn<Account, String> email;
    @FXML
    private TableColumn<Account, Boolean> admin;
    @FXML
    private TableColumn<Account, Blob> accpicture;

    @FXML
    private TableView<Item> itemTableView;
    @FXML
    private TableColumn<Item, Integer> id;
    @FXML
    private TableColumn<Item, String> itemName;
    @FXML
    private TableColumn<Item, Double> price;
    @FXML
    private TableColumn<Item, String> description;
    @FXML
    private TableColumn<Item, String> condition;
    @FXML
    private TableColumn<Item, String> category;
    @FXML
    private TableColumn<Item, Blob> picture;

    /*
    public void handleEditsInAccountsTableView(TableColumn.CellEditEvent editedCell) {  // Method that takes care of what should happen if a cell is edited in accountsTableView
        if (editedCell.getSource() == username) {
            Account selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
            System.out.println("Old Value: " + selectedAccount.getUserName());
            selectedAccount.setUserName(editedCell.getNewValue().toString());
            System.out.println("New Value: " + selectedAccount.getUserName());
        } else if (editedCell.getSource() == password) {
            Account selectedAccount = accountsTableView.getSelectionModel().getSelectedItem();
            System.out.println("Old Value: " + selectedAccount.getPassword());
            selectedAccount.setPassword(editedCell.getNewValue().toString());
            System.out.println("New Value: " + selectedAccount.getPassword());
        }

    }
    */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
        retrieveAccounts();
        retrieveItems();
        setupColumns();
        handleEditAccountsButton(); // Default selected choice
    }

    @FXML
    private void handleEditAccountsButton() {
        selectEditAccounts();
        accountsTableActive = true; // Currently working tableview = accounts
    }

    @FXML
    private void handleEditItemsButton() {
        selectEditItems();
        accountsTableActive = false;    // Currently working tableview = sales
    }

    private void selectEditAccounts() { // Handles all visual changes when editAccounts is pressed.
        editItems.setStyle("-fx-background-color:  #4f4d4d;");
        editAccounts.setStyle("-fx-background-color:  #46ab57;");
        accountsTableView.setVisible(true);
        itemTableView.setVisible(false);
        accountInputs.setVisible(true);
    }

    private void selectEditItems() { // Handles all visual changes when editSales is pressed.
        editAccounts.setStyle("-fx-background-color:  #4f4d4d;");
        editItems.setStyle("-fx-background-color:  #46ab57;");
        itemTableView.setVisible(true);
        accountsTableView.setVisible(false);
        accountInputs.setVisible(false);
    }

    private void retrieveAccounts() {    // Retrievees all accounts from DB and places them as objects in observable list accounts.
        accounts = FXCollections.observableArrayList();
        accounts = dbHandler.retrieveAllAccounts();
        /*
        for (Account account : accounts) {
            System.out.println(account);
        }

         */

    }

    private void retrieveItems() {   // Retrieves all sales from the DB and places them as objects in observable list sales
        items = FXCollections.observableArrayList();
        items = dbHandler.retrieveAllSales();
        /*
        for (Sale sale : sales) {
            System.out.println(sale);
        }

         */
    }

    private void setupColumns() {
        // Account Columns
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        // username.setCellFactory(TextFieldTableCell.forTableColumn());   // Makes cells in column editable
        password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        admin.setCellValueFactory(new PropertyValueFactory<>("Admin"));
        accpicture.setCellValueFactory(new PropertyValueFactory<>("Picture"));
        accountsTableView.setItems(accounts);
        accountsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Allows for multiple rows to be selected
        // Sales Columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        condition.setCellValueFactory(new PropertyValueFactory<>("Condition"));
        category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        picture.setCellValueFactory(new PropertyValueFactory<>("Picture"));
        itemTableView.setItems(items);
        itemTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void handleRemoveButton() { // Removes selected rows in table view from database by fetching email addresses and IDs.
        if (accountsTableActive) {  // Currently displayed tableview is accounts
            ObservableList<Account> selectedAccounts = accountsTableView.getSelectionModel().getSelectedItems();
            if (!selectedAccounts.isEmpty()) {
                ArrayList<String> accountEmailsToBeRemoved = new ArrayList<>();
                StringBuilder confirmationMessage = new StringBuilder("Are you sure you wish to delete the following accounts: ");
                for (int i = 0; i < selectedAccounts.size(); i++) {
                    confirmationMessage.append("\n [Account ").append(i + 1).append("] ").append(selectedAccounts.get(i).getEmail());
                    accountEmailsToBeRemoved.add(selectedAccounts.get(i).getEmail());
                }
                confirmationMessage.append("\n\n").append("They will be permanently removed!");
                Optional<ButtonType> action = MessageHandler.getConfirmationAlert("Confirmation", null, confirmationMessage.toString()).showAndWait();

                if (action.get() == ButtonType.OK) {
                    dbHandler.removeAccounts(accountEmailsToBeRemoved);
                    accounts.removeAll(selectedAccounts);
                }

            } else {
                MessageHandler.getErrorAlert("Error", null, "No accounts selected.").showAndWait();
            }

        } else {  // Currently displayed tableview is sales
            ObservableList<Item> selectedItems = itemTableView.getSelectionModel().getSelectedItems();
            if (!selectedItems.isEmpty()) {
                ArrayList<Integer> saleIdsToBeRemoved = new ArrayList<>();
                StringBuilder confirmationMessage = new StringBuilder("Are you sure you wish to delete the following sales: ");
                for (int i = 0; i < selectedItems.size(); i++) {
                    confirmationMessage.append("\n [Sale ").append(i + 1).append("] ID: ").append(selectedItems.get(i).getId()).append(" Name: ").append(selectedItems.get(i).getName());
                    saleIdsToBeRemoved.add(selectedItems.get(i).getId());
                }
                confirmationMessage.append("\n\n").append("They will be permanently removed!");
                Optional<ButtonType> action = MessageHandler.getConfirmationAlert("Confirmation", null, confirmationMessage.toString()).showAndWait();

                if (action.get() == ButtonType.OK) {
                    dbHandler.removeSales(saleIdsToBeRemoved);
                    items.removeAll(selectedItems);
                }

            } else {
                MessageHandler.getErrorAlert("Error", null, "No sales selected.").showAndWait();
            }
        }
    }

    @FXML
    private void handleInsertAccountButton() {
        // Logic for inserting a new account into appropriate tableview
    }

    @FXML
    private void handleInsertItemButton() {
        // Logic for inserting a new sale into appropriate tableview
    }

    @FXML
    private void handleUpdateButton() {
        // Here for the memes?
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
