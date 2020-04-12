package Models;

import javafx.scene.control.Alert;

public abstract class MessageHandler {
    private static Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private static Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private static Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);

    public static Alert getErrorAlert(String alertTitle, String alertHeader, String alertContent) {
        errorAlert.setTitle(alertTitle);
        errorAlert.setHeaderText(alertHeader);
        errorAlert.setContentText(alertContent);
        return errorAlert;
    }

    public static Alert getConfirmationAlert(String alertTitle, String alertHeader, String alertContent) {
        confirmationAlert.setResizable(true);
        confirmationAlert.setTitle(alertTitle);
        confirmationAlert.setHeaderText(alertHeader);
        confirmationAlert.setContentText(alertContent);
        return confirmationAlert;
    }

    public static Alert getInformationAlert(String alertTitle, String alertHeader, String alertContent) {
        informationAlert.setResizable(true);
        informationAlert.setTitle(alertTitle);
        informationAlert.setHeaderText(alertHeader);
        informationAlert.setContentText(alertContent);
        return informationAlert;
    }


}
