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
}