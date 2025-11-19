package model;

public class Clothes extends Product {

    // Constructor with ID (from DB)
    public Clothes(int id, String name, double price, double purchasePrice,
                   int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied, "Clothes");
    }

    // Constructor without ID (new product)
    public Clothes(String name, double price, double purchasePrice,
                   int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied, "Clothes");
    }
}
