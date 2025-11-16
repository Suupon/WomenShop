package model;

public abstract class Product {

    protected int id;
    protected String name;
    protected double price;
    protected double purchasePrice;
    protected int nbItems;
    protected boolean discountApplied;

    public Product(int id, String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.nbItems = nbItems;
        this.discountApplied = discountApplied;
    }

    public Product(String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        this(-1, name, price, purchasePrice, nbItems, discountApplied);
    }

    // Getters / Setters

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getPurchasePrice() { return purchasePrice; }
    public int getNbItems() { return nbItems; }
    public boolean isDiscountApplied() { return discountApplied; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public void setNbItems(int nbItems) { this.nbItems = nbItems; }
    public void setDiscountApplied(boolean discountApplied) { this.discountApplied = discountApplied; }
}
