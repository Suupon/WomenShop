package model;

public class Accessory extends Product {

    public Accessory(int id, String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied);
    }

    public Accessory(String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied);
    }
}
