package controllers;

import java.sql.SQLException;
import dao.CustomerDao;
import java.util.ArrayList;
import model.Customer;

/**
 *
 * @author jacobobc
 */
public class CustomersController {
    
    private final CustomerDao customerDao;
    
    public CustomersController() {
        customerDao = new CustomerDao();
    }
    
    public ArrayList<Customer> listAllCustomers() {
        return customerDao.listAllCustomers();
    }
    
    public Customer selectCustomer(String id) {
        return customerDao.selectCustomer(id);
    }
    
    public void insertCustomer(Customer customer) throws SQLException {
        customerDao.insertCustomer(customer);
    }
    
    public void updateCustomer(Customer customer) throws SQLException {
        customerDao.updateCustomer(customer);
    }
    
    public void deleteCustomer(String id) {
        customerDao.deleteCustomer(id);
    }
    
}
