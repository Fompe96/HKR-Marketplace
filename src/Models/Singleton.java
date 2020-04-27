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
    ObservableList<Item> itemObservableList;

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

    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }




    public String encryptPass(String password) {
        int key = 1;
        char[] passChar = password.toCharArray();

        for (int i = 0; i < passChar.length; i++) {
            char encryptLetter = passChar[i];
            encryptLetter = (char) (encryptLetter + key);
            if (encryptLetter >= 'a' && encryptLetter <= 'z') {
                if (encryptLetter < 'a') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            } else if (encryptLetter >= 'A' && encryptLetter <= 'Z') {
                if (encryptLetter < 'A') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'Z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            }
            passChar[i] = encryptLetter;
        }
        return new String(passChar);
    }

    public String decryptPass(String password) {
        int key = -1;
        char[] passChar = password.toCharArray();

        for (int i = 0; i < passChar.length; i++) {
            char encryptLetter = passChar[i];
            encryptLetter = (char) (encryptLetter + key);
            if (encryptLetter >= 'a' && encryptLetter <= 'z') {
                if (encryptLetter < 'a') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            } else if (encryptLetter >= 'A' && encryptLetter <= 'Z') {
                if (encryptLetter < 'A') {
                    encryptLetter = (char) (encryptLetter + 26);
                }
                if (encryptLetter > 'Z') {
                    encryptLetter = (char) (encryptLetter - 26);
                }
            }

            passChar[i] = encryptLetter;
        }


        return new String(passChar);
    }

}