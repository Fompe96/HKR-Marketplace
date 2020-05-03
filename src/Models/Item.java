package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;

public class Item {


    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleStringProperty description;
    private SimpleStringProperty condition;
    private SimpleStringProperty category;
    private File imageFile;
    private Image image;
    private ImageView imageView;
    private Blob picture;
    private ImageView photo;
    private SimpleStringProperty owner;

    public Item(){
    }

    public Item(Integer id, String name, double price, String description, String condition, String category, Blob picture, String ownerEmail) {   // Constructor used when creating retrieved items.
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.condition = new SimpleStringProperty(condition);
        this.category = new SimpleStringProperty(category);
        this.picture = picture;
        this.owner = new SimpleStringProperty(ownerEmail);
    }

    public Item(String name, Double price, String description, String condition, String category, File imageFile, String ownerEmail) { // Constructor used when creating new items to be inserted into DB.
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.condition = new SimpleStringProperty(condition);
        this.category = new SimpleStringProperty(category);
        this.imageFile = imageFile;
        this.owner = new SimpleStringProperty(ownerEmail);
    }

    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
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

    public ImageView getPhoto() {
        return photo;
    }

    public Blob getPicture() {
        return picture;
    }

    public String getOwner() {
        return owner.get();
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

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public void setImage() {
        if(picture != null) {
          //  this.image = new Image((InputStream) picture);
        }
        else{
            System.out.println("Cannot find picture");
        }
    }

    public void setImageView(Image image){
        this.imageView = new ImageView(image);
    }

    public Image getImage(){
        return image;
    }

    public ImageView getImageView(){
        return imageView;
    }





    @Override
    public String toString() {
        return "Sale{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", description='" + getDescription() + '\'' +
                ", condition='" + getCondition() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", picture='" + getPicture() + '\'' +
                ", imageFile='" + getImageFile() + '\'' +
                ", owner='" + getOwner() + '\'' +
                '}';
    }
}
