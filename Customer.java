/**
 * Smart Online Shopping Management System
 * Customer.java — Stores customer details entered at startup
 *
 * OOP Concepts: Encapsulation, Constructors
 */
public class Customer {
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name    = name;
        this.email   = email;
        this.address = address;
    }

    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getAddress() { return address; }
}
