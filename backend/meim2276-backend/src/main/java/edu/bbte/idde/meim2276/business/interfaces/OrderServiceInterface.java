package edu.bbte.idde.meim2276.business.interfaces;

import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderServiceInterface {
    void addOrder(Order order) throws BadArgumentException, DatabaseException;

    void deleteOrder(int id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    void updateOrder(Order order) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    Order getOrder(int id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    List<Order> getAllOrders() throws DatabaseException;
}
