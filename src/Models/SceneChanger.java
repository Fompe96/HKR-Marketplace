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
    private static Stage popupStage;

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

    public static Stage getPopupStage() {
        if (popupStage == null) {
            popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(SceneChanger.programStage);
            popupStage.setResizable(false);
            popupStage.initStyle(StageStyle.TRANSPARENT);
        }
        return popupStage;
    }
}