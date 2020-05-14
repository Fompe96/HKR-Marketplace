package Controllers;

import Database.DBHandler;
import Models.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MonitoringSalesController implements Initializable {

    private ObservableList<Item> favorites;
    private ObservableList<Item> userSales;
    private double x, y;
    private boolean activateClickOnItem;


    @FXML
    private Button sellButton, marketButton, settingsButton, adminButton, closingButton, minimizeButton,removeSale;

    @FXML
    private ToggleButton viewFav,viewSale;

    @FXML
    private ImageView adminView;

    @FXML
    private TableView<Item> table;

    @FXML
    private TableColumn<Item, ImageView> pic;

    @FXML
    private TableColumn<Item, String> title;

    @FXML
    private TableColumn<Item, Double> price;

    @FXML
    private TableColumn<Item, String> description;

    @FXML
    private Text text;

    @FXML
    private void handleMinimizeButton(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void handleClosingButton() {
        Platform.exit();
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
    private void handleLogOutButton() {
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    public void sellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml");
    }

    @FXML
    private void handleSettingsButton() {
        SceneChanger.changeScene("../Views/Settings.fxml");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml");
    }

    @FXML
    private void handleSellBuyButton() {
        SceneChanger.changeScene("../Views/MonitoringSales.fxml");
    }

    @FXML
    private void handleMarketButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml");
    }

    private void initializeFavTable() {
        ObservableList <Item> favorites = Singleton.getInstance().getUserFavorites();
        for (Item item : favorites) {
            if (item.getImage() != null) {
                item.setPic(item.getImage());   // Här sätter jag varje objekts imageview till dens aktuella bild
            }
        }

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Allows for multiple rows to be selected
        pic.setCellValueFactory(new PropertyValueFactory<>("pic"));
        title.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        table.setItems(favorites);
    }

    private void handleToolTip() {
        ToolTipHandler.getToolTipCloseButton(closingButton);
        ToolTipHandler.getToolTipMinimizeButton(minimizeButton);
        ToolTipHandler.getTooltipMarketPlace(marketButton);
        ToolTipHandler.getToolTipSettings(settingsButton);
        ToolTipHandler.getToolTipAdmin(adminButton);
        ToolTipHandler.getToolTipSellScene(sellButton);
    }

    private void handleCursor() {
        adminButton.setCursor(Cursor.HAND);
        settingsButton.setCursor(Cursor.HAND);
        marketButton.setCursor(Cursor.HAND);
        sellButton.setCursor(Cursor.HAND);
    }

    private void fetchFavFromDB() {
        favorites = DBHandler.retrieveAllFavorites(Singleton.getInstance().getLoggedInEmail());
    }

    private void fetchSalesFromDB(){
        userSales = DBHandler.retrieveUserSales(Singleton.getInstance().getLoggedInEmail());
    }

    private void handleClickOnItem() {

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                if (table.getSelectionModel().getSelectedItem() != null && activateClickOnItem) {
                    Singleton.getInstance().setItem(table.getSelectionModel().getSelectedItem());
                    SceneChanger.changeScene("../Views/MonitoringSalesPreview.fxml");
                }
            }
        });
    }

    @FXML
    private void removeSale() {
        ObservableList<Item> selectedItems = table.getSelectionModel().getSelectedItems();
        if (!selectedItems.isEmpty()) {
            ArrayList<Integer> saleIdsToBeRemoved = new ArrayList<>();
            StringBuilder confirmationMessage = new StringBuilder("Are you sure you wish to delete the following sales: ");
            for (int i = 0; i < selectedItems.size(); i++) {
                confirmationMessage.append("\n Sale ").append(i + 1).append(" Name: ").append(selectedItems.get(i).getName());
                saleIdsToBeRemoved.add(selectedItems.get(i).getId());
            }
            confirmationMessage.append("\n\n").append("They will be permanently removed!");
            Optional<ButtonType> action = MessageHandler.getConfirmationAlert("Confirmation", null, confirmationMessage.toString()).showAndWait();

            if (action.get() == ButtonType.OK) {
                DBHandler.removeSales(saleIdsToBeRemoved);
                userSales.removeAll(selectedItems);
            }

        } else {
            MessageHandler.getErrorAlert("Error", null, "No sales selected.").showAndWait();
        }
    }



    private void handleAdminView() {
        if (Singleton.getInstance().getLoggedInAccount().isAdmin()) {
            adminView.setVisible(true);
            adminButton.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchFavFromDB();
        fetchSalesFromDB();
        initializeFavTable();
        activateClickOnItem = true;
        handleClickOnItem();
        handleCursor();
        handleToolTip();
        handleAdminView();
        removeSale.setVisible(false);
        viewSale.setVisible(true);
        viewFav.setVisible(false);
    }

    @FXML
    private void toggleSalesButton(){
        text.setText("Sales");
        removeSale.setVisible(true);
        viewSale.setVisible(false);
        viewFav.setVisible(true);
        activateClickOnItem = false;
        for (Item item : userSales) {
            if (item.getImage() != null) {
                item.setPic(item.getImage());   // Här sätter jag varje objekts imageview till dens aktuella bild
            }
        }
        /*
        pic.setCellValueFactory(new PropertyValueFactory<>("pic"));
        title.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
       */
        table.setItems(userSales);

    }

    @FXML
    private void toggleFavouriteButton(){
        text.setText("Favourites");
        removeSale.setVisible(false);
        activateClickOnItem = true;
        viewSale.setVisible(true);
        viewFav.setVisible(false);
        initializeFavTable();
    }

}
