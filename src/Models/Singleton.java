package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private ObservableList<Item> itemObservableList;
    private String lastInsertedObject;

    public void setItemObservableList(){
         itemObservableList = FXCollections.observableArrayList();
    }

    public ObservableList getItemObservableList(){
        return itemObservableList;
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

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

}