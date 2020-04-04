package Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneChanger {

    private static Stage programStage;

    public static void changeScene(String path, String title) {
        try {
            AnchorPane root = FXMLLoader.load(SceneChanger.class.getResource(path));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Resources/CSS.css");
            programStage.setTitle(title);
            programStage.setScene(scene);
            programStage.show();
            programStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProgramStage(Stage programStage) {
        SceneChanger.programStage = programStage;
    }
}