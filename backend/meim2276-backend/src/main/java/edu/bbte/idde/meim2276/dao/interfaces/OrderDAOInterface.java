package edu.bbte.idde.meim2276.dao.interfaces;

import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.exceptions.DatabaseException;

import java.util.List;

public interface OrderDAOInterface {
    void addOrder(Order order) throws DatabaseException;

    void deleteOrder(int id) throws DatabaseException;

    void updateOrder(Order order) throws DatabaseException;

    Order getOrder(int id) throws DatabaseException;

    List<Order> getAllOrders() throws DatabaseException;
}
