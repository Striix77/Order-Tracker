package edu.bbte.idde.meim2276.backend.dao.factories;

import edu.bbte.idde.meim2276.backend.dao.implementations.OrderDAODatabaseImpl;
import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("DB")
public class OrderDBFactory implements DaoFactory {
    private final transient OrderDAODatabaseImpl orderDAODatabaseImpl;

    @Autowired
    public OrderDBFactory(OrderDAODatabaseImpl orderDAODatabaseImpl) {
        this.orderDAODatabaseImpl = orderDAODatabaseImpl;
    }

    @Override
    public OrderDAOInterface createOrderService() {
        return orderDAODatabaseImpl;
    }
}