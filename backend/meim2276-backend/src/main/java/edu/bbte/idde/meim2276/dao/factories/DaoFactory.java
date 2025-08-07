package edu.bbte.idde.meim2276.dao.factories;

import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;

public interface DaoFactory {
    OrderDAOInterface createOrderService();
}
