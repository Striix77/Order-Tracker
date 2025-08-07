package edu.bbte.idde.meim2276.backend.business.services;

import edu.bbte.idde.meim2276.backend.business.interfaces.OrderServiceInterface;
import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService implements OrderServiceInterface {
    private final transient OrderDAOInterface orderDAO;

    @Autowired
    public OrderService(OrderDAOInterface orderDAO) {
        this.orderDAO = orderDAO;
    }


    @Override
    public Long addOrder(PurchaseOrder purchaseOrder)
            throws BadArgumentException, DatabaseException {
        if (purchaseOrder.getBuyerId() < 0
                || purchaseOrder.getDateOfOrder().isEmpty() || purchaseOrder.getDateOfDelivery().isEmpty()
                || purchaseOrder.getStatus().isEmpty() || purchaseOrder.getTotal() < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        return orderDAO.addOrder(purchaseOrder);
    }

    @Override
    public void deleteOrder(Long buyerId) throws BadArgumentException, OrderNotFoundException,
            DatabaseException {
        if (buyerId < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(buyerId)) {
            throw new OrderNotFoundException("Order with id " + buyerId + " not found!", null);
        }
        orderDAO.deleteOrder(buyerId);
    }

    @Override
    public void updateOrder(PurchaseOrder purchaseOrder, Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException {
        if (id < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found!", null);
        }
        orderDAO.updateOrder(purchaseOrder, id);
    }

    @Override
    public PurchaseOrder getOrder(Long id) throws BadArgumentException, OrderNotFoundException, DatabaseException {
        if (id < 0) {
            throw new BadArgumentException("Invalid input!", null);
        }
        if (orderExist(id)) {
            throw new OrderNotFoundException("Order with id " + id + " not found!", null);
        }
        return orderDAO.getOrder(id);
    }

    @Override
    public List<PurchaseOrder> getAllOrders() throws DatabaseException {
        return orderDAO.getAllOrders();
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatus(String status) throws DatabaseException {
        if (status.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByStatus(status);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByName(String name) throws DatabaseException {
        if (name.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByName(name);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByBuyerId(Long buyerId) throws DatabaseException {
        if (buyerId < 0) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByBuyerId(buyerId);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) throws DatabaseException {
        if (status.isEmpty() || buyerId < 0) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByStatusAndBuyerId(status, buyerId);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderBefore(String before) throws DatabaseException {
        if (before.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByDateOfOrderBefore(before);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderAfter(String after) throws DatabaseException {
        if (after.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByDateOfOrderAfter(after);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryBefore(String before) throws DatabaseException {
        if (before.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByDateOfDeliveryBefore(before);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryAfter(String after) throws DatabaseException {
        if (after.isEmpty()) {
            throw new DatabaseException("Invalid input!", null);
        }
        return orderDAO.getAllOrdersByDateOfDeliveryAfter(after);
    }


    private boolean orderExist(Long id) throws DatabaseException {
        List<PurchaseOrder> allPurchaseOrders;
        allPurchaseOrders = orderDAO.getAllOrders();
        for (PurchaseOrder purchaseOrder : allPurchaseOrders) {
            if (Objects.equals(purchaseOrder.getId(), id)) {
                return false;
            }
        }
        return true;
    }

}
