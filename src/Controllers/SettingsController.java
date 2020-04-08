package Controllers;

import Database.DBHandler;
import Models.SceneChanger;
import Models.Singleton;
import com.sun.source.tree.UsesTree;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    DBHandler dbHandler = new DBHandler();

    @FXML
    private Label loggedInAs;
    private double x, y;

    private File file;

    @FXML
    private ImageView imageToUpload;

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
    private void handleUploadImage() throws FileNotFoundException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String user = Singleton.getInstance().getLoggedInEmail();
            dbHandler.uploadImage(user, file.getPath());
        }
    }


    @FXML
    private void handleMarketButton() {
        SceneChanger.changeScene("../Views/Marketplace.fxml", "Marketplace");
    }

    @FXML
    public void sellButton() {
        SceneChanger.changeScene("../Views/Sell.fxml", "Sell");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInAs.setText(Singleton.getInstance().getLoggedInName());
        dbHandler.getConnection();
        try {
            getProfilePicture();
        } catch (NullPointerException | SQLException ignored) {
        }
    }

    private void getProfilePicture() throws SQLException {
        String email = Singleton.getInstance().getLoggedInEmail();
        String SQL = "select Picture from account where Email = ?;";
        PreparedStatement pstmt = dbHandler.getConnection().prepareStatement(SQL);
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            InputStream imageFile = rs.getBinaryStream(1);
            Image image = new Image(imageFile);
            imageToUpload.setImage(image);
        }
    }
}
