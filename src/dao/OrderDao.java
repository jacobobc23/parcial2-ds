package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Customer;
import model.Order;
import org.mariadb.jdbc.Connection;
import singleton.Singleton;

/**
 *
 * @author Jacobo-bc
 */
public class OrderDao {

    private final Connection con;

    public OrderDao() {
        con = Singleton.getINSTANCE().getConnection();
    }

    public ArrayList<Customer> listCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                Customer Customer = new Customer(id, name, email);
                customers.add(Customer);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return customers;
    }

    public ArrayList<Order> listAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try ( PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs;

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double total = rs.getDouble("total");
                String customerId = rs.getString("customer_id");

                Customer customer = getCustomer(customerId);

                Order order = new Order(date, customer, id, total);
                orders.add(order);
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return orders;
    }

    public ArrayList<Order> listCustomerOrders(String id) {
        ArrayList<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";

        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double total = rs.getDouble("total");
                
                Customer customer = getCustomer(id);
                
                Order order = new Order(date, customer, orderId, total);
                orders.add(order);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return orders;
    }

    public Order selectOrder(int id) {
        String query = "SELECT * FROM orders WHERE id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs;

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                double total = rs.getDouble("total");
                String customerId = rs.getString("customer_id");

                Customer customer = getCustomer(customerId);

                return new Order(date, customer, id, total);
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return null;
    }

    public void insertOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (date, total, customer_id) VALUES (?, ?, ?)";
        try ( PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(order.getDate()));
            ps.setDouble(2, order.getTotal());
            ps.setString(3, order.getCustomer().getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET total = ?, customer_id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, order.getTotal());
            ps.setString(2, order.getCustomer().getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void deleteOrder(int id) {
        String query = "DELETE FROM orders WHERE id = ?";
        try ( PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    private Customer getCustomer(String id) {
        CustomerDao customerDao = new CustomerDao();
        return customerDao.selectCustomer(id);
    }

}
