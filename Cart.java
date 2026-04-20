/**
 * Smart Online Shopping Management System
 * Cart.java — Shopping cart and order management
 * OOP Concepts: Encapsulation, Composition, ArrayList, Constructors
 */

import java.util.ArrayList;

// CartItem: holds a product and its quantity
class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product  = product;
        this.quantity = quantity;
    }
        public Product getProduct()  { return product; }
        public int     getQuantity() { return quantity; }

    public void addQuantity(int qty) {
        this.quantity += qty;
    }
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("  %-22s  x%-3d  Rs. %-8.2f  =  Rs. %.2f",
                product.getName(), quantity, product.getPrice(), getSubtotal());
    }
}

//Cart: contains a list of CartItems
public class Cart {
    private ArrayList<CartItem> items = new ArrayList<>();
    private Customer owner;

    public Cart(Customer owner) {
        this.owner = owner;
    }

    public void addItem(Product product, int quantity) {
        if (!product.isAvailable()) {
            System.out.println("  Sorry, '" + product.getName() + "' is out of stock.");
            return;
        }
        if (quantity > product.getStock()) {
            System.out.println("  Only " + product.getStock() + " unit(s) available.");
            return;
        }
        for (CartItem ci : items) {
            if (ci.getProduct().getProductId() == product.getProductId()) {
                ci.addQuantity(quantity);
                System.out.println("  Updated quantity for: " + product.getName());
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        System.out.println("  ✓ Added to cart: " + product.getName());
    }

    public void removeItem(int productId) {
        for (CartItem ci : items) {
            if (ci.getProduct().getProductId() == productId) {
                if (ci.getQuantity() > 1) {
                    ci.addQuantity(-1);
                    System.out.println("  Quantity reduced for: " + ci.getProduct().getName());
                } else {
                    items.remove(ci);
                    System.out.println("  Item removed from cart: " + ci.getProduct().getName());
                }
                return;
            }
        }
        System.out.println("  Item not found in cart.");
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("  Your cart is empty.");
            return;
        }
        System.out.println("\n  -------- Your Cart --------");
        for (CartItem ci : items) {
            System.out.println(ci);
        }
        System.out.println("  ---------------------------");
        System.out.printf("  %-30s  Rs. %.2f%n", "TOTAL:", getTotal());
        System.out.println("  ---------------------------");
    }

    public double getTotal() {
        double total = 0;
        for (CartItem ci : items) total += ci.getSubtotal();
        return total;
    }

    public boolean isEmpty() { return items.isEmpty(); }

    public ArrayList<CartItem> getItems() { return items; }

    public void clear() { items.clear(); }
}

//Order: represents a confirmed purchase
class Order {
    private static int orderCounter = 1000;

    private int orderId;
    private Customer customer;
    private ArrayList<CartItem> items;
    private double totalAmount;

    public Order(Customer customer, ArrayList<CartItem> cartItems, double total) {
        this.orderId     = ++orderCounter;
        this.customer    = customer;
        this.items       = new ArrayList<>(cartItems);
        this.totalAmount = total;

        for (CartItem ci : this.items) {
            ci.getProduct().reduceStock(ci.getQuantity());
        }
    }

    public int getOrderId() { return orderId; }

    public void printConfirmation() {
        System.out.println("\n  ✓ Order #" + orderId + " placed successfully!");
        System.out.println("  ==========================================");
        System.out.println("  Customer  : " + customer.getName());
        System.out.println("  Email     : " + customer.getEmail());
        System.out.println("  Delivering to: " + customer.getAddress());
        System.out.println("  ------------------------------------------");
        for (CartItem ci : items) System.out.println(ci);
        System.out.println("  ------------------------------------------");
        System.out.printf("  %-30s  Rs. %.2f%n", "TOTAL PAID:", totalAmount);
        System.out.println("  ==========================================");
        System.out.println("  Estimated Delivery: 40 - 50 minutes");
        System.out.println("  Your electronics are on the way! ⚡");
        System.out.println("  ==========================================\n");
    }
}
