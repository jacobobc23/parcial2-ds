package dao;

import exceptions.OrderCustomerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Customer;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author Jacobo-bc
 */
public class CustomerDao {

    private final Connection con;

    public CustomerDao() {
        con = Singleton.getINSTANCE().getConnection();
    }

    public ArrayList<Customer> listCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                customers.add(getCustomer(rs));
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return customers;
    }

    public Customer selectCustomer(String id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs;

            ps.setString(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return getCustomer(rs);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    public void insertCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customers (id, name, email) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getId());

            ps.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void deleteCustomer(String id) {
        String query = "DELETE FROM customers WHERE id = ? AND NOT EXISTS (SELECT 1 FROM orders WHERE customer_id = ?)";
        try ( PreparedStatement ps = con.prepareStatement(query)) {

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

    private Customer getCustomer(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String email = rs.getString("email");

        return new Customer(id, name, email);

    }
}
