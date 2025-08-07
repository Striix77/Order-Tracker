package edu.bbte.idde.meim2276.backend.dao.factories;

import edu.bbte.idde.meim2276.backend.dao.implementations.OrderDAOInMemImpl;
import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("InMem")
public class OrderInMemFactory implements DaoFactory {
    private final transient OrderDAOInMemImpl orderDAOInMemImpl;

    @Autowired
    public OrderInMemFactory(OrderDAOInMemImpl orderDAOInMemImpl) {
        this.orderDAOInMemImpl = orderDAOInMemImpl;
    }

    @Override
    public OrderDAOInterface createOrderService() {
        return orderDAOInMemImpl;
    }
}