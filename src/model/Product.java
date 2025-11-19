package model;

import javafx.beans.property.*;

public class Product {

    private IntegerProperty id;
    private StringProperty name;
    private DoubleProperty price;             // selling price
    private DoubleProperty purchasePrice;     // cost price
    private IntegerProperty nbItems;
    private BooleanProperty discountApplied;
    private StringProperty category;

    // Constructor with ID (for DB)
    public Product(int id, String name, double price, double purchasePrice,
                   int nbItems, boolean discountApplied, String category) {

        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.nbItems = new SimpleIntegerProperty(nbItems);
        this.discountApplied = new SimpleBooleanProperty(discountApplied);
        this.category = new SimpleStringProperty(category);
    }

    // Constructor WITHOUT ID (for new products)
    public Product(String name, double price, double purchasePrice,
                   int nbItems, boolean discountApplied, String category) {

        this.id = new SimpleIntegerProperty(0); // temp before DB autoincrement
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.purchasePrice = new SimpleDoubleProperty(purchasePrice);
        this.nbItems = new SimpleIntegerProperty(nbItems);
        this.discountApplied = new SimpleBooleanProperty(discountApplied);
        this.category = new SimpleStringProperty(category);
    }

    // GETTERS
    public int getId() { return id.get(); }
    public String getName() { return name.get(); }
    public double getPrice() { return price.get(); }
    public double getPurchasePrice() { return purchasePrice.get(); }
    public int getNbItems() { return nbItems.get(); }
    public boolean isDiscountApplied() { return discountApplied.get(); }
    public String getCategory() { return category.get(); }

    // PROPERTY GETTERS (for TableView)
    public IntegerProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public DoubleProperty priceProperty() { return price; }
    public DoubleProperty purchasePriceProperty() { return purchasePrice; }
    public IntegerProperty nbItemsProperty() { return nbItems; }
    public BooleanProperty discountAppliedProperty() { return discountApplied; }
    public StringProperty categoryProperty() { return category; }

    // SETTERS
    public void setId(int id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setPrice(double price) { this.price.set(price); }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice.set(purchasePrice); }
    public void setNbItems(int nbItems) { this.nbItems.set(nbItems); }
    public void setDiscountApplied(boolean discountApplied) { this.discountApplied.set(discountApplied); }
    public void setCategory(String category) { this.category.set(category); }
}
