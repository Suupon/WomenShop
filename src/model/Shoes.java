package model;

public class Shoes extends Product {

    public Shoes(int id, String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(id, name, price, purchasePrice, nbItems, discountApplied);
    }

    public Shoes(String name, double price, double purchasePrice, int nbItems, boolean discountApplied) {
        super(name, price, purchasePrice, nbItems, discountApplied);
    }
}
