package edu.bbte.idde.meim2276.business.services;

import edu.bbte.idde.meim2276.business.interfaces.OrderServiceInterface;
import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.exceptions.OrderNotFoundException;

import java.util.List;


public class OrderService implements OrderServiceInterface {

    private final transient OrderDAOInterface orderDAO;


    public OrderService(OrderDAOInterface orderDAO) {
        this.orderDAO = orderDAO;
    }


    @Override
    public void addOrder(Order order)
            throws BadArgumentException, DatabaseException {
        if (order.getBuyerId() < 0 || order.getDateOfOrder().isEmpty() || order.getDateOfDelivery().isEmpty()
                || order.getStatus().isEmpty() || order.getTotal() < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        orderDAO.addOrder(order);
    }

    @Override
    public void deleteOrder(int buyerId) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException {
        if (buyerId < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(buyerId)) {
            throw new OrderNotFoundException("Order with id " + buyerId + " not found!", null);
        }
        orderDAO.deleteOrder(buyerId);
    }

    @Override
    public void updateOrder(Order order) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException {
        int id = order.getId();
        if (id < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found!", null);
        }
        orderDAO.updateOrder(order);
    }

    @Override
    public Order getOrder(int id) throws BadArgumentException, OrderNotFoundException, DatabaseException,
            BadConnectionException {
        if (id < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found!", null);
        }
        return orderDAO.getOrder(id);
    }

    @Override
    public List<Order> getAllOrders() throws DatabaseException {
        return orderDAO.getAllOrders();
    }

    private boolean orderExist(int id) throws DatabaseException {
        List<Order> allOrders;
        allOrders = orderDAO.getAllOrders();
        for (Order order : allOrders) {
            if (order.getId() == id) {
                return false;
            }
        }
        return true;
    }

}
