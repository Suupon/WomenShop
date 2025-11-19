package model;

import java.time.LocalDateTime;

public class Transaction {

    private int id;
    private int productId;
    private String category;
    private String type;
    private int qty;
    private double unitPrice;     // prix unitaire
    private double totalAmount;   // prix total = qty * unitPrice
    private LocalDateTime date;

    public Transaction(int id,
                       int productId,
                       String category,
                       String type,
                       int qty,
                       double unitPrice,
                       LocalDateTime date) {
        this.id = id;
        this.productId = productId;
        this.category = category;
        this.type = type;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalAmount = qty * unitPrice;
        this.date = date;
    }

    // constructeur utilisé pour les INSERT
    public Transaction(int productId,
                       String category,
                       String type,
                       int qty,
                       double unitPrice) {
        this(0, productId, category, type, qty, unitPrice, LocalDateTime.now());
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public int getQty() { return qty; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getDate() { return date; }
}
