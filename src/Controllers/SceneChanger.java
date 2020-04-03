package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneChanger {

    private SceneChanger() {
    }

    public static void changeScene(ActionEvent event, String path, String title) {
        try {
            Node eventSource = (Node) event.getSource();
            Scene scene = eventSource.getScene();
            Stage stage = (Stage) scene.getWindow();

            Parent root = FXMLLoader.load(SceneChanger.class.getResource(path));
            Scene newScene = new Scene(root);

            stage.setTitle(title);
            stage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to change scene");
        }
    }

    public static void changeScene(ActionEvent event, String path) {
        changeScene(event, path, "Title");
    }
}