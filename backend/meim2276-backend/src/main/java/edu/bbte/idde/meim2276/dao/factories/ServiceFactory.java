package edu.bbte.idde.meim2276.dao.factories;

import edu.bbte.idde.meim2276.business.interfaces.OrderServiceInterface;

public interface ServiceFactory {
    OrderServiceInterface createOrderService();
}
