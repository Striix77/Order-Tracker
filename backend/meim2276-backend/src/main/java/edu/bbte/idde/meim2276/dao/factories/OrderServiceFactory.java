package edu.bbte.idde.meim2276.dao.factories;

import edu.bbte.idde.meim2276.business.interfaces.OrderServiceInterface;
import edu.bbte.idde.meim2276.business.services.OrderService;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;

public class OrderServiceFactory implements ServiceFactory {

    @Override
    public OrderServiceInterface createOrderService() {
        DaoFactory factory = WebShopFactory.getFactory();
        OrderDAOInterface orderDAO = factory.createOrderService();
        return new OrderService(orderDAO);
    }
}
