package controllers;

import java.sql.SQLException;
import dao.OrderDao;
import java.util.ArrayList;
import model.Customer;
import model.Order;

/**
 *
 * @author jacobobc
 */
public class OrdersController {

    private final OrderDao orderDao;

    public OrdersController() {
        orderDao = new OrderDao();
    }

    public ArrayList<Customer> listAllCustomers() {
        return orderDao.listAllCustomers();
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
