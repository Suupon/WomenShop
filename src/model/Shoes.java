package model;

public class Shoes extends Product {

    // Constructor with ID (from DB)
    public Shoes(int id, String name, double price, double purchasePrice,
                 int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied, "Shoes");
    }

    // Constructor without ID (new product)
    public Shoes(String name, double price, double purchasePrice,
                 int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied, "Shoes");
    }
}
