package Controllers;

import Database.DBHandler;
import Models.*;
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
        try {
            if (!(file.length() <= 0)) {
                if (validateIfRememberLoginChecked()) {
                    try {
                        readUserCredentialsFromFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FadeTransition ft = new FadeTransition(Duration.millis(2000), logoPane);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                FadeTransition ftSecond = new FadeTransition(Duration.millis(2000), loginInformationPane);
                ftSecond.setFromValue(0);
                ftSecond.setToValue(1);
                ftSecond.play();
                Platform.runLater(() -> root.requestFocus());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                Account foundUserAccount = DBHandler.findUser(userEmail.getText(), userPassword.getText());
                if (foundUserAccount != null) {
                    Singleton.getInstance().setLoggedInAccount(foundUserAccount);
                    Platform.runLater(() -> SceneChanger.changeScene("../Views/Marketplace.fxml"));
                } else {
                    loadingImage.setOpacity(0);
                    Platform.runLater(() -> loggingInLabel.setText("Failed to login"));
                    Platform.runLater(() -> loggingInLabel.setTextFill(Color.RED));
                    Platform.runLater(() -> MessageHandler.getErrorAlert("Error", "Error", "Login credentials not found").showAndWait());
                }
            }
        }).start();
    }

    private void writeUserCredentialsToFile() {
        try {
            boolean rememberLoginButtonBoolean = false;
            if (rememberLoginButton.isSelected()) {
                rememberLoginButtonBoolean = true;
            }
            String email = encryptPass(userEmail.getText());
            String password = encryptPass(userPassword.getText());
            FileWriter myWriter = new FileWriter("userCredentials.txt");
            myWriter.write(email + "\n" + password + "\n" + rememberLoginButtonBoolean);
            myWriter.close();
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
        userEmail.setText(decryptPass(loginCredentialsList.get(0)));
        userPassword.setText(decryptPass(loginCredentialsList.get(1)));
        if (loginCredentialsList.get(2).equals("true")) {
            rememberLoginButton.setSelected(true);
        } else {
            rememberLoginButton.setSelected(false);
        }
    }

    @FXML
    private void uncheckRememberLogin() throws IOException {
        if (!rememberLoginButton.isSelected()) {
            int i = 0;
            BufferedReader bufReader = new BufferedReader(new FileReader("userCredentials.txt"));
            ArrayList<String> loginCredentialsList = new ArrayList<>();
            String line = bufReader.readLine();
            while (line != null) {
                loginCredentialsList.add(i, line);
                i++;
                line = bufReader.readLine();
            }
            String email = encryptPass(userEmail.getText());
            String password = encryptPass(userPassword.getText());
            FileWriter myWriter = new FileWriter("userCredentials.txt");
            myWriter.write(email + "\n" + password + "\n" + false);
            myWriter.close();
        }
    }

    private boolean validateIfRememberLoginChecked() throws IOException {
        int i = 0;
        BufferedReader bufReader = new BufferedReader(new FileReader("userCredentials.txt"));
        ArrayList<String> loginCredentialsList = new ArrayList<>();
        String line = bufReader.readLine();
        while (line != null) {
            loginCredentialsList.add(i, line);
            i++;
            line = bufReader.readLine();
        }
        if (loginCredentialsList.get(2).equals("true")) {
            return true;
        } else {
            return false;
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

    private String encryptPass(String password) {
        int key = 1;
        char[] passChar = password.toCharArray();

        for (int i = 0; i < passChar.length; i++) {
            char encryptLetter = passChar[i];
            encryptLetter = (char) (encryptLetter + key);
            if (encryptLetter >= 'a' && encryptLetter <= 'z') {
                if (encryptLetter < 'a') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            } else if (encryptLetter >= 'A' && encryptLetter <= 'Z') {
                if (encryptLetter < 'A') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'Z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            }
            passChar[i] = encryptLetter;
        }
        return new String(passChar);
    }

    private String decryptPass(String password) {
        int key = -1;
        char[] passChar = password.toCharArray();

        for (int i = 0; i < passChar.length; i++) {
            char encryptLetter = passChar[i];
            encryptLetter = (char) (encryptLetter + key);
            if (encryptLetter >= 'a' && encryptLetter <= 'z') {
                if (encryptLetter < 'a') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            } else if (encryptLetter >= 'A' && encryptLetter <= 'Z') {
                if (encryptLetter < 'A') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'Z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            }
            passChar[i] = encryptLetter;
        }
        return new String(passChar);
    }
}