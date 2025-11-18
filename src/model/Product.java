package model;

import javafx.beans.property.*;

public class Product {

    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private StringProperty category;

    public Product(int id, String name, double price, String category) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.category = new SimpleStringProperty(category);
    }

    // Getters
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public double getPrice() { return price.get(); }
    public String getCategory() { return category.get(); }

    // Property Getters (OBLIGATOIRE pour TableView)
    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public DoubleProperty priceProperty() { return price; }
    public StringProperty categoryProperty() { return category; }

    // Setters
    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setPrice(double price) { this.price.set(price); }
    public void setCategory(String category) { this.category.set(category); }
}
