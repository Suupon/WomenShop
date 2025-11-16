package model;

public class Clothes extends Product {

    public Clothes(int id, String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied);
    }

    public Clothes(String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied);
    }
}
