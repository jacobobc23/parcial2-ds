package dao;

import exceptions.OrderCustomerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Customer;
import org.mariadb.jdbc.Connection;
import db.DBConnection;

/**
 *
 * @author jacobobc
 */
public class CustomerDao {

    private final Connection con;

    public CustomerDao() {
        con = DBConnection.getINSTANCE().getConnection();
    }

    public ArrayList<Customer> listAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customers.add(getCustomer(rs));
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return customers;
    }

    public Customer selectCustomer(String id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getCustomer(rs);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    public void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (id, name, email) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void deleteCustomer(String id) {
        String sql = "DELETE FROM customers WHERE id = ? AND NOT EXISTS (SELECT 1 FROM orders WHERE customer_id = ?)";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, id);
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted == 0) {
                throw new OrderCustomerException();
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public Customer getCustomer(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String email = rs.getString("email");

        return new Customer(id, name, email);
    }
}
