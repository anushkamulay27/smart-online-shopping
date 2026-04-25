/**
 *   Smart Online Shopping Management System as a group Project
 *   Main.java — Entry Point & CLI Menu
 *   OOP Concepts Demonstrated:
 *     • Classes & Objects      (Product, Customer, Cart, Order)
 *     • Encapsulation          (private fields, public getters/setters)
 *     • Composition            (Cart contains CartItems; Order holds Cart data)
 *     • Constructors           (all classes)
 *     • ArrayList & Array Collections
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Product> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //Seed inventory
        inventory.add(new Product(101, "Arduino Uno",       549.00, 10));
        inventory.add(new Product(102, "Resistors (Pack)",   89.00, 50));
        inventory.add(new Product(103, "Connection Wires",  149.00, 30));
        inventory.add(new Product(104, "USB Cables",        199.00, 20));

        //Welcome & Customer Details
        printBanner();
        System.out.println("  Please enter your details to get started.\n");
        System.out.print("  Name    : ");
        String name = scanner.nextLine().trim();
        System.out.print("  Email   : ");
        String email = scanner.nextLine().trim();
        System.out.print("  Address : ");
        String address = scanner.nextLine().trim();
        Customer customer = new Customer(name, email, address);
        Cart cart = new Cart(customer);
        System.out.println("\n  Welcome, " + customer.getName() + "! Let's start shopping.\n");

        //Main Menu Loop
        while (true) {
            System.out.println("  ===== Smart Online Shopping =====");
            System.out.println("  1. Browse Products");
            System.out.println("  2. Add Item to Cart");
            System.out.println("  3. View Cart");
            System.out.println("  4. Remove Item from Cart");
            System.out.println("  5. Place Order");
            System.out.println("  6. Exit");
            System.out.print("\n  Enter choice: ");

            int choice = intInput();
            switch (choice) {
                case 1 -> browseProducts();
                case 2 -> addToCart(cart);
                case 3 -> cart.viewCart();
                case 4 -> {
                    System.out.print("  Enter Product ID to remove: ");
                    cart.removeItem(intInput());
                }
                case 5 -> placeOrder(customer, cart);
                case 6 -> {
                    System.out.println("\n  Thank you for shopping with us, "
                            + customer.getName() + "! Goodbye. 👋\n");
                    return;
                }
                default -> System.out.println("  Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    //Browse all products
    static void browseProducts() {
        System.out.println("\n  -------- Products --------");
        for (Product p : inventory) {
            System.out.println("  " + p);
        }
    }

    //Add product to cart
    static void addToCart(Cart cart) {
        browseProducts();
        System.out.print("  Enter Product ID: ");
        int pid = intInput();
        Product found = null;
        for (Product p : inventory) {
            if (p.getProductId() == pid) {
                found = p;
                break;
            }
        }
        if (found == null) {
            System.out.println("  Product not found. Please try again.");
            return;
        }

        System.out.print("  Enter Quantity  : ");
        int qty = intInput();
        cart.addItem(found, qty);
    }

    //Place order
    static void placeOrder(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("  Your cart is empty. Add items before placing an order.");
            return;
        }
        cart.viewCart();
        System.out.print("  Confirm order? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("  Order cancelled.");
            return;
        }
        Order order = new Order(customer, cart.getItems(), cart.getTotal());
        cart.clear();
        order.printConfirmation();
    }

    //Safe integer input
    static int intInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("  Please enter a valid number: ");
            }
        }
    }

    //Banner
    static void printBanner() {
        System.out.println("<   Smart Online Shopping System   >");
    }
}
