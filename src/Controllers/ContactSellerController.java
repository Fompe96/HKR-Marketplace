package Controllers;

import Database.DBHandler;
import Models.Account;
import Models.Item;
import Models.SceneChanger;
import Models.Singleton;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactSellerController implements Initializable {

    private ObservableList<Account> accounts;

    private double x,y;

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label rating;

    @FXML
    private ImageView profilePic;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Account itemOwner = DBHandler.findUser(Singleton.getInstance().getItem().getOwner());
        //fetchAccountsDB();

        if (Singleton.getInstance().getItem() != null && itemOwner != null) {
            if (itemOwner.getEmail().equals(Singleton.getInstance().getItem().getOwner())) {
                name.setText(itemOwner.getUserName());
                email.setText(itemOwner.getEmail());
                try {
                    profilePic.setImage(itemOwner.getImage());
                } catch (NullPointerException e) {
                }
            }
            rating.setText("6.9");
        }
    }
            /*
            System.out.println(Singleton.getInstance().getItem().getOwner());
            for (Account account : accounts) {
                System.out.println(account.getEmail());
                if (Singleton.getInstance().getItem() != null && account != null) {
                    if (account.getEmail().equals(Singleton.getInstance().getItem().getOwner())) {
                        name.setText(account.getUserName());
                        email.setText(account.getEmail());
                            try {
                                profilePic.setImage(account.getImage());
                            }catch (NullPointerException e) {
                            }
                        }
                        rating.setText("6.9");
                    }
                }
            }

    public void fetchAccountsDB(){
        accounts = DBHandler.retrieveAllAccounts();
    }
    */


    @FXML
    private void handleLogOutButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Login.fxml");
    }

    @FXML
    public void sellButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Sell.fxml");
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
    private void handleSettingsButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/Settings.fxml");
    }

    @FXML
    private void handleAdminButton() {
        SceneChanger.changeScene("../Views/Administration.fxml");
    }

    @FXML
    private void handleMonitoringButton() {
        Singleton.getInstance().setItem(null);
        SceneChanger.changeScene("../Views/MonitoringSales.fxml");
    }


}
