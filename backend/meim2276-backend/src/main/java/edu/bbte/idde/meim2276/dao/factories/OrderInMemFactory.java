package edu.bbte.idde.meim2276.dao.factories;

import edu.bbte.idde.meim2276.dao.implementations.OrderDAOInMemImpl;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;

public class OrderInMemFactory implements DaoFactory {
    private static volatile OrderDAOInterface instance;

    @Override
    public OrderDAOInterface createOrderService() {
        if (instance == null) {
            instance = new OrderDAOInMemImpl();
        }
        return instance;
    }
}