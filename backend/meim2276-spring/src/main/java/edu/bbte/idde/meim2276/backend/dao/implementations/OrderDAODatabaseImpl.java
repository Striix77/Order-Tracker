package edu.bbte.idde.meim2276.backend.dao.implementations;

import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.backend.dao.repositories.OrderRepository;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("DB")
public class OrderDAODatabaseImpl implements OrderDAOInterface {
    private static final Logger log = LoggerFactory.getLogger(OrderDAODatabaseImpl.class);
    private final transient OrderRepository orderRepository;

    @Autowired
    public OrderDAODatabaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    @Transactional
    public void deleteOrder(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found: " + id, null);
        }
        orderRepository.deleteById(id);
        log.info("Order deleted: {}", id);

    }

    @Override
    @Transactional
    public void deleteAllOrdersByUserId(Long id) throws OrderNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found: " + id, null);
        }
        orderRepository.deleteOrdersByUserId(id);
        log.info("Orders deleted from user: {}", id);

    }

    @Override
    @Transactional
    public void updateOrder(PurchaseOrder purchaseOrder, Long id) throws OrderNotFoundException {

        if (orderRepository.existsById(id)) {
            purchaseOrder.setId(id);
            orderRepository.save(purchaseOrder);
            log.info("Order updated: {}", id);
        } else {
            throw new OrderNotFoundException("Order not found: " + id, null);
        }

    }

    @Override
    public PurchaseOrder getOrder(Long id) throws DatabaseException {
        try {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new OrderNotFoundException("Order not found: " + id, null));
        } catch (OrderNotFoundException e) {
            throw new DatabaseException("Database Exception: ", e);
        }
    }


    @Override
    public List<PurchaseOrder> getAllOrders() {

        return orderRepository.findAll();

    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByName(String name) {
        return orderRepository.findByNameContainingIgnoreCase(name);

    }

    @Override
    public List<PurchaseOrder> getAllOrdersByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) {
        return orderRepository.findByStatusAndBuyerId(status, buyerId);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderBefore(String before) {

        return orderRepository.findByDateOfOrderBefore(before);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderAfter(String after) {
        return orderRepository.findByDateOfOrderAfter(after);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryBefore(String before) {
        return orderRepository.findByDateOfDeliveryBefore(before);
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryAfter(String after) {
        return orderRepository.findByDateOfDeliveryAfter(after);
    }

    @Override
    @Transactional
    public Long addOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder savedPurchaseOrder = orderRepository.save(purchaseOrder);

        log.info("Order added: {}", savedPurchaseOrder.getId());
        return savedPurchaseOrder.getId();

    }

}