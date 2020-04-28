package Controllers;

import Database.DBHandler;
import Models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    private double x, y;
    private ObservableList<Account> accounts;
    private ObservableList<Item> items;
    private boolean accountsTableActive;

    @FXML
    private Button editAccounts, editItems, minimizeButton, closingButton, sellButton, marketPlaceButton, settingsButton;
    @FXML
    private AnchorPane root;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveAccounts();
        retrieveItems();
        setupColumns();
        handleEditAccountsButton(); // Default selected choice
        handleToolTip();
        sellButton.setCursor(Cursor.HAND);
        settingsButton.setCursor(Cursor.HAND);
        marketPlaceButton.setCursor(Cursor.HAND);
        Platform.runLater(() -> root.requestFocus());
    }

    private void handleToolTip() {
        ToolTipHandler.getToolTipCloseButton(closingButton);
        ToolTipHandler.getToolTipMinimizeButton(minimizeButton);
        ToolTipHandler.getTooltipMarketPlace(marketPlaceButton);
        ToolTipHandler.getToolTipSettings(settingsButton);
        ToolTipHandler.getToolTipSellScene(sellButton);
    }

    @FXML
    private void handleEditAccountsButton() {
        editItems.setStyle("-fx-background-color:  #4f4d4d;");
        editAccounts.setStyle("-fx-background-color:  #46ab57;");
        accountsTableView.setVisible(true);
        itemTableView.setVisible(false);
        accountsTableActive = true; // Currently working tableview = accounts
    }

    @FXML
    private void handleEditItemsButton() {
        editAccounts.setStyle("-fx-background-color:  #4f4d4d;");
        editItems.setStyle("-fx-background-color:  #46ab57;");
        itemTableView.setVisible(true);
        accountsTableView.setVisible(false);
        accountsTableActive = false;    // Currently working tableview = sales
    }

    private void retrieveAccounts() {    // Retrievees all accounts from DB and places them as objects in observable list accounts.
        accounts = FXCollections.observableArrayList();
        accounts = DBHandler.retrieveAllAccounts();
    }

    private void retrieveItems() {   // Retrieves all sales from the DB and places them as objects in observable list items
        items = FXCollections.observableArrayList();
        items = DBHandler.retrieveAllSales();
    }

    private void setupColumns() {
        // Account Columns
        username.setCellValueFactory(new PropertyValueFactory<>("userName"));
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
            removeAccount();
        } else {  // Currently displayed tableview is items
            removeItem();
        }
    }

    private void removeAccount() {
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
                DBHandler.removeAccounts(accountEmailsToBeRemoved);
                accounts.removeAll(selectedAccounts);
            }

        } else {
            MessageHandler.getErrorAlert("Error", null, "No accounts selected.").showAndWait();
        }
    }

    private void removeItem() {
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
                DBHandler.removeSales(saleIdsToBeRemoved);
                items.removeAll(selectedItems);
            }

        } else {
            MessageHandler.getErrorAlert("Error", null, "No sales selected.").showAndWait();
        }
    }

    @FXML
    private void handleInsertButton() {
        if (accountsTableActive) {
            loadInsertAccountView();
        } else {
            loadInsertItemView();
        }
    }

    private void loadInsertAccountView() {
        Stage popupStage = SceneChanger.getPopupStage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Views/insertAccount.fxml"));
            Scene insertAccountScene = new Scene(root);
            insertAccountScene.getStylesheets().add("Resources/CSS.css");
            popupStage.setScene(insertAccountScene);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInsertItemView() {
        Stage popupStage = SceneChanger.getPopupStage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Views/insertItem.fxml"));
            Scene insertItemScene = new Scene(root);
            insertItemScene.getStylesheets().add("Resources/CSS.css");
            popupStage.setScene(insertItemScene);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButton() {
        // Here for the memes?
    }

    @FXML
    private void handleSellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
    }

    @FXML
    private void handleMarketPlaceButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }

    @FXML
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml");
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
