package Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneChanger {

    private static Stage programStage;

    public static void changeScene(String path) {
        try {
            Parent root = FXMLLoader.load(SceneChanger.class.getResource(path));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Resources/CSS.css");
            programStage.setScene(scene);
            programStage.show();
            programStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void setProgramStage(Stage programStage) {
        SceneChanger.programStage = programStage;
    }
}