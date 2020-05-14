package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Singleton {

    private static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Account loggedInAccount;  // Variable used to keep track of the currently logged in account.
    private Item item;
    private String lastInsertedObject;
    private Object objectToEdit;
    private ObservableList<Account> accounts;
    private ObservableList <Item> userFavorites;

    public ObservableList getUserFavorites() {
        return userFavorites;
    }

    public void setUserFavorites(ObservableList userFavorites) {
        this.userFavorites = userFavorites;
    }

    public ObservableList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ObservableList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public String getLoggedInEmail() {
        return loggedInAccount.getEmail();
    }

    public String getLoggedInName() {
        return loggedInAccount.getUserName();
    }

    public void setLastInsertedObject(String lastInsertedObject) {
        this.lastInsertedObject = lastInsertedObject;
    }

    public String getLastInsertedObject() {
        return lastInsertedObject;
    }

    public void setObjectToEdit(Object objectToEdit) {
        this.objectToEdit = objectToEdit;
    }

    public Object getObjectToEdit() {
        return objectToEdit;
    }

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

}