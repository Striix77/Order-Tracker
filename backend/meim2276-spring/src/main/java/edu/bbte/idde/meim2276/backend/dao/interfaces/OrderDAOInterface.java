package edu.bbte.idde.meim2276.backend.dao.interfaces;

import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderDAOInterface {
    Long addOrder(PurchaseOrder purchaseOrder) throws DatabaseException;

    void deleteOrder(Long id) throws DatabaseException, OrderNotFoundException;

    void deleteAllOrdersByUserId(Long userId) throws OrderNotFoundException;

    void updateOrder(PurchaseOrder purchaseOrder, Long id) throws OrderNotFoundException;

    PurchaseOrder getOrder(Long id) throws DatabaseException;

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
