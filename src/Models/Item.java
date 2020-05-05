package Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Item {


    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleStringProperty description;
    private SimpleStringProperty condition;
    private SimpleStringProperty category;
    private File imageFile;
    private SimpleStringProperty owner;
    private ImageView pic; // Used to show the imageFile in tableview
    private SimpleBooleanProperty saleActive;

    public Item() {

    }

    public Item(int id, String name, Double price, String description, String condition, String category, File imageFile, String ownerEmail, Boolean active) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.description = new SimpleStringProperty(description);
        this.condition = new SimpleStringProperty(condition);
        this.category = new SimpleStringProperty(category);
        this.imageFile = imageFile;
        this.owner = new SimpleStringProperty(ownerEmail);
        this.saleActive = new SimpleBooleanProperty(active);
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

    public ImageView getPic() {
        return pic;
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

    public boolean isSaleActive() {
        return saleActive.get();
    }

    public void setSaleActive(boolean saleActive) {
        this.saleActive.set(saleActive);
    }

    public SimpleBooleanProperty saleActiveProperty() {
        return saleActive;
    }

    public void setPic(Image image) {
        this.pic = new ImageView(image);
        this.pic.setFitHeight(130);
        this.pic.setFitWidth(175);
    }

    public Image getImage() {
        return new Image(getImageFile().toURI().toString());
    }

    @Override
    public String toString() {
        return "Sale{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", description='" + getDescription() + '\'' +
                ", condition='" + getCondition() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", imageFile='" + getImageFile() + '\'' +
                ", owner='" + getOwner() + '\'' +
                ", saleActive='" + isSaleActive() + '\'' +
                '}';
    }
}
