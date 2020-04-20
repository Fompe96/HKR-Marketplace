package Models;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneChanger.setProgramStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Login.fxml"));
        primaryStage.setTitle("HKR-Marketplace");
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("Resources/home_icon_46ab57.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.getStylesheets().add("Resources/CSS.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        EmailSender emailSender = new EmailSender();
        emailSender.SaveToPdf("Thanks");

    }


    public static void main(String[] args) {
        launch(args);
    }
}
