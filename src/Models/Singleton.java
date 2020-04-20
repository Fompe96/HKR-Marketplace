package Models;

import javafx.scene.image.Image;

import java.io.File;

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
    private String itemName;
    private Double priceOfProduct;
    private String description;
    private String category;
    private String condition;
    private String image;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPriceOfProduct() {
        return priceOfProduct;
    }

    public void setPriceOfProduct(Double priceOfProduct) {
        this.priceOfProduct = priceOfProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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