package Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class Account {
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleBooleanProperty admin;
    private File imageFile;
    private ArrayList<Item> favourites;


    public Account(String userName, String password, String email, boolean admin, File pictureAsFile) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.admin = new SimpleBooleanProperty(admin);
        this.imageFile = pictureAsFile;
        this.favourites =new ArrayList<>();
    }

    public String getUserName() {
        return userName.get();
    }


    public String getPassword() {
        return password.get();
    }

    public String getEmail() {
        return email.get();
    }

    public boolean isAdmin() {
        return admin.get();
    }


    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public void setAdmin(boolean admin) {
        this.admin = new SimpleBooleanProperty(admin);
    }


    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Image getImage() {
        return new Image(getImageFile().toURI().toString());
    }

    @Override
    public String toString() {
        return "Account{" +
                " userName='" + getUserName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", admin='" + isAdmin() + '\'' +
                ", pictureImage='" + getImage() + '\'' +
                '}';
    }
}
