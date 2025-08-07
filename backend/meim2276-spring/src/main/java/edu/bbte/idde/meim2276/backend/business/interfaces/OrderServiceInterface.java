package edu.bbte.idde.meim2276.backend.business.interfaces;

import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderServiceInterface {
    Long addOrder(PurchaseOrder purchaseOrder) throws BadArgumentException, DatabaseException;

    void deleteOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    void updateOrder(PurchaseOrder purchaseOrder, Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    PurchaseOrder getOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    List<PurchaseOrder> getAllOrders() throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByStatus(String status) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByName(String name) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByBuyerId(Long buyerId) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByDateOfOrderBefore(String before) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByDateOfOrderAfter(String after) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByDateOfDeliveryBefore(String before) throws DatabaseException;

    List<PurchaseOrder> getAllOrdersByDateOfDeliveryAfter(String after) throws DatabaseException;

}
