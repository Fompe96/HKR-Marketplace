package Controllers;

import Database.DBHandler;
import Models.Item;
import Models.SceneChanger;
import Models.Singleton;
import Models.ToolTipHandler;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MonitoringSalesController implements Initializable {

    private ObservableList<Item> favorites;
    private ObservableList<Item> userSales;
    private double x, y;

    @FXML
    private Button sellButton, marketButton, settingsButton, adminButton, closingButton, minimizeButton;

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
        for (Item item : favorites) {
            if (item.getImage() != null) {
                item.setPic(item.getImage());   // Här sätter jag varje objekts imageview till dens aktuella bild
            }
        }

        pic.setCellValueFactory(new PropertyValueFactory<>("pic"));
        title.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        table.setItems(favorites);

        handleCursor();
        handleToolTip();
        handleAdminView();
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
                if (table.getSelectionModel().getSelectedItem() != null) {
                    Singleton.getInstance().setItem(table.getSelectionModel().getSelectedItem());
                    SceneChanger.changeScene("../Views/MonitoringSalesPreview.fxml");
                }
            }
        });
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
        handleClickOnItem();
    }

    @FXML
    private void toggleSalesButton(){
        text.setText("Sales");
        System.out.println(userSales.get(1));
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
        initializeFavTable();
    }

}
