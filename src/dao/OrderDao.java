package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Customer;
import model.Order;
import org.mariadb.jdbc.Connection;
import db.DBConnection;

/**
 *
 * @author jacobobc
 */
public class OrderDao {

    private final Connection con;
    private final CustomerDao customerDao;

    public OrderDao() {
        con = DBConnection.getINSTANCE().getConnection();
        customerDao = new CustomerDao();
    }

    public ArrayList<Customer> listAllCustomers() {
        return customerDao.listAllCustomers();
    }

    public ArrayList<Order> listAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

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

    public ArrayList<Order> listCustomerOrders(String customerId) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double total = rs.getDouble("total");

                Customer customer = getCustomer(customerId);

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

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

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
        String sql = "INSERT INTO orders (date, total, customer_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(order.getDate()));
            ps.setDouble(2, order.getTotal());
            ps.setString(3, order.getCustomer().getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET total = ?, customer_id = ?";
       
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, order.getTotal());
            ps.setString(2, order.getCustomer().getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }

    private Customer getCustomer(String id) {
        return customerDao.selectCustomer(id);
    }

}
