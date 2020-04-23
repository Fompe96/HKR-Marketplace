package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Blob;

public class Item {


    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleStringProperty description;
    private SimpleStringProperty condition;
    private SimpleStringProperty category;
    private String picturePath;
    private Blob picture;

    public Item(){
    }

    public Item(Integer id, String name, double price, String description, String condition, String category, Blob picture) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.condition = new SimpleStringProperty(condition);
        this.category = new SimpleStringProperty(category);
        this.picture = picture;
    }


    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public double getPrice() {
        return price.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getCondition() {
        return condition.get();
    }

    public String getCategory() {
        return category.get();
    }


    public Blob getPicture() {
        return picture;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public void setCondition(String condition) {
        this.condition = new SimpleStringProperty(condition);
    }

    public void setCategory(String category) {
        this.category = new SimpleStringProperty(category);
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", description='" + getDescription() + '\'' +
                ", condition='" + getCondition() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", picture='" + getPicture() + '\'' +
                '}';
    }
}
