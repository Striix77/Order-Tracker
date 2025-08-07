package edu.bbte.idde.meim2276.backend.dao.factories;

import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;

public interface DaoFactory {
    OrderDAOInterface createOrderService();
}
