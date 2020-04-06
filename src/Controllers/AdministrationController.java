package Controllers;

import Database.DBHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministrationController implements Initializable {

    private DBHandler dbHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dbHandler == null) {
            dbHandler = new DBHandler();
        }
    }
}
