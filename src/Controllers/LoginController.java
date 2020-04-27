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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField userPassword;

    @FXML
    private CheckBox rememberLoginButton;

    @FXML
    private Label loggingInLabel;

    @FXML
    private ImageView loadingImage;

    @FXML
    private TextField userEmail;

    @FXML
    private AnchorPane root;
    @FXML
    private Pane logoPane;
    @FXML
    private Pane loginInformationPane;

    @FXML
    private Button minimizeButton, closingButton, loginButton;

    private double x, y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("userCredentials.txt");
        if (!(file.length() <= 0)) {
            try {
                readUserCredentialsFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        loginButton.setCursor(Cursor.HAND);
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
        if (rememberLoginButton.isSelected()) {
            writeUserCredentialsToFile();
        }
        new Thread(() -> {
            Platform.runLater(() -> loggingInLabel.setTextFill(Color.WHITE));
            if (userEmail.getText().equals("") || userPassword.getText().equals("")) {
                Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Please enter all fields").showAndWait());
            } else {
                Image image = new Image("Resources/loadingImage.gif");
                loadingImage.setImage(image);
                loadingImage.setOpacity(100);
                Platform.runLater(() -> loggingInLabel.setText("Logging in..."));
                loggingInLabel.setOpacity(100);
                DBHandler.getConnection();
                boolean checkIfExists = DBHandler.findUser(userEmail.getText(), userPassword.getText());
                if (checkIfExists) {
                    Singleton.getInstance().setLoggedInAccount(DBHandler.getUserInformation(userEmail.getText()));
                    Platform.runLater(() -> SceneChanger.changeScene("../Views/Marketplace.fxml"));
                } else {
                    loadingImage.setOpacity(0);
                    Platform.runLater(() -> loggingInLabel.setText("Failed to login"));
                    Platform.runLater(() -> loggingInLabel.setTextFill(Color.RED));
                    Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Login credentials not found").showAndWait());
                }
                DBHandler.closeConnection();
            }
        }).start();
    }

    private void writeUserCredentialsToFile() {
        try {
            FileWriter myWriter = new FileWriter("userCredentials.txt");
            myWriter.write(userEmail.getText() + "\n" + userPassword.getText());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            readUserCredentialsFromFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void readUserCredentialsFromFile() throws IOException {
        int i = 0;
        BufferedReader bufReader = new BufferedReader(new FileReader("userCredentials.txt"));
        ArrayList<String> loginCredentialsList = new ArrayList<>();
        String line = bufReader.readLine();
        while (line != null) {
            loginCredentialsList.add(i, line);
            i++;
            line = bufReader.readLine();
        }
        bufReader.close();
        userEmail.setText(loginCredentialsList.get(0));
        userPassword.setText(loginCredentialsList.get(1));
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