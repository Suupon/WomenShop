package model;

import java.sql.Timestamp;

public class Transaction {

    private int id;
    private int productId;
    private String category;
    private String type;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private Timestamp date;

    public Transaction(int productId, String category, String type, int quantity, double unitPrice) {
        this.productId = productId;
        this.category = category;
        this.type = type;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = unitPrice * quantity;
    }

    // Getters
    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalAmount() { return totalAmount; }
    public Timestamp getDate() { return date; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setDate(Timestamp date) { this.date = date; }
}
