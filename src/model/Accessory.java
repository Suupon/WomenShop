package model;

public class Accessory extends Product {

    // Constructor with ID (from DB)
    public Accessory(int id, String name, double price, double purchasePrice,
                     int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied, "Accessory");
    }

    // Constructor without ID (new product)
    public Accessory(String name, double price, double purchasePrice,
                     int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied, "Accessory");
    }
}
