package Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Blob;
import java.util.ArrayList;

public class Account {
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleBooleanProperty admin;
    private Blob picture;
    private ArrayList<Item> favourites;

    public Account(String userName, String password, String email, boolean admin, Blob picture) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.admin = new SimpleBooleanProperty(admin);
        this.picture = picture;
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

    public Blob getPicture() {
        return picture;
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

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Account{" +
                " userName='" + getUserName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", admin='" + isAdmin() + '\'' +
                ", picture='" + getPicture() + '\'' +
                '}';
    }
}
