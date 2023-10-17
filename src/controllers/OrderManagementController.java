package controllers;

import java.sql.SQLException;
import dao.OrderDao;
import java.util.ArrayList;
import model.Customer;
import model.Order;

/**
 *
 * @author Jacobo-bc
 */
public class OrderManagementController {

    private final OrderDao orderDao;

    public OrderManagementController() {
        orderDao = new OrderDao();
    }

    public ArrayList<Customer> listCustomers() {
        return orderDao.listCustomers();
    }

    public ArrayList<Order> listAllOrders() {
        return orderDao.listAllOrders();
    }

    public ArrayList<Order> listCustomerOrders(String id) {
        return orderDao.listCustomerOrders(id);
    }

    public Order selectOrder(int id) {
        return orderDao.selectOrder(id);
    }

    public void insertOrder(Order order) throws SQLException {
        orderDao.insertOrder(order);
    }

    public void updateOrder(Order order) throws SQLException {
        orderDao.updateOrder(order);
    }

    public void deleteOrder(int id) {
        orderDao.deleteOrder(id);
    }
}
