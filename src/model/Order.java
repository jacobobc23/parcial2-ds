package model;

import java.time.LocalDate;

/**
 *
 * @author Jacobo-bc
 */
public class Order {
    
    private final LocalDate date;
    private Customer customer;
    private int id;
    private double total;

    public Order(Customer customer, double total) {
        this.date = LocalDate.now();
        this.customer = customer;
        this.total = total;
    }
    
    public Order(LocalDate date, Customer customer, int id, double total) {
        this.date = date;
        this.customer = customer;
        this.id = id;
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
