package Models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public abstract class SceneChanger {

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


    private static Stage createPopupStage() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(SceneChanger.programStage);
        popupStage.setResizable(false);
        popupStage.initStyle(StageStyle.TRANSPARENT);
        return popupStage;
    }

    // Method used to create popup scenes throughout the program
    public static void createPopupWindow(String fxmlPath) {
        try {
            Stage popupStage = createPopupStage();
            Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlPath));
            Scene insertAccountScene = new Scene(root);
            insertAccountScene.getStylesheets().add("Resources/CSS.css");
            popupStage.setScene(insertAccountScene);
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            MessageHandler.getErrorAlert("Error", "Error", "Error creating a new popup stage.").showAndWait();
        }
    }
}