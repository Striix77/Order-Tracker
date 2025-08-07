package edu.bbte.idde.meim2276.dao.factories;

import edu.bbte.idde.meim2276.dao.implementations.OrderDAODatabaseImpl;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;

public class OrderDBFactory implements DaoFactory {
    private static volatile OrderDAOInterface instance;

    @Override
    public OrderDAOInterface createOrderService() {
        if (instance == null) {
            instance = new OrderDAODatabaseImpl();
        }
        return instance;

    }
}