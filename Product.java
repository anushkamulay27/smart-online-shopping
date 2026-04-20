/**
 * Smart Online Shopping Management System
 * Product.java — Represents a product in the store
 *
 * OOP Concepts: Encapsulation, Constructors, toString
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name      = name;
        this.price     = price;
        this.stock     = stock;
    }

    public int    getProductId() { return productId; }
    public String getName()      { return name; }
    public double getPrice()     { return price; }
    public int    getStock()     { return stock; }

    public boolean isAvailable() {
        return stock > 0;
    }

    public void reduceStock(int qty) {
        if (qty > stock) {
            throw new IllegalArgumentException("Not enough stock for: " + name);
        }
        this.stock -= qty;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-22s  Rs. %-8.2f | Stock: %d",
                productId, name, price, stock);
    }
}
