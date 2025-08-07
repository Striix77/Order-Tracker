package edu.bbte.idde.meim2276.backend.dao.implementations;

import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@Profile("InMem")
public class OrderDAOInMemImpl implements OrderDAOInterface {
    private final transient AtomicInteger idCounter = new AtomicInteger();
    private final transient Map<Long, PurchaseOrder> orders;
    private static final Logger log = LoggerFactory.getLogger(OrderDAOInMemImpl.class);

    public OrderDAOInMemImpl(Map<Long, PurchaseOrder> orders) {
        this.orders = orders;
    }

    public OrderDAOInMemImpl() {
        this.orders = new HashMap<>();
    }

    @Override
    public void deleteOrder(Long id) throws OrderNotFoundException {
        if (!orders.containsKey(id)) {
            throw new OrderNotFoundException("Order with id" + id + " not found!", null);
        }
        log.info("Order deleted: {}", id);
        orders.remove(id);
    }

    @Override
    public void deleteAllOrdersByUserId(Long userId) throws OrderNotFoundException {
        if (!orders.containsKey(userId)) {
            throw new OrderNotFoundException("Database is empty!", null);
        }
        orders.values().removeIf(order -> order.getBuyerId().equals(userId));
        log.info("Orders deleted from user: {}", userId);
    }

    @Override
    public void updateOrder(PurchaseOrder purchaseOrder, Long id) throws OrderNotFoundException {
        if (!orders.containsKey(id)) {
            throw new OrderNotFoundException("Order with id" + id + " not found!", null);
        }
        orders.remove(id);
        purchaseOrder.setId(id);
        orders.put(id, purchaseOrder);
        log.info("Order updated: {}", id);
        log.info("{}", orders.get(id).getBuyerId());
        log.info(orders.get(id).getDateOfOrder());
        log.info(orders.get(id).getDateOfDelivery());
        log.info(orders.get(id).getStatus());
        log.info("{}", orders.get(id).getTotal());
    }

    @Override
    public PurchaseOrder getOrder(Long id) {
        for (PurchaseOrder purchaseOrder : orders.values()) {
            if (Objects.equals(purchaseOrder.getId(), id)) {
                log.info("Order retrieved: {}", id);
                return purchaseOrder;
            }
        }
        log.info("Order with id {} not found!", id);
        return null;
    }

    @Override
    public List<PurchaseOrder> getAllOrders() {
        log.info("All orders retrieved.");
        return orders.values().stream().toList();
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatus(String status) {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !order.getStatus().equals(status));
        log.info("All orders by status retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByName(String name) {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !order.getName().contains(name));
        log.info("All orders by name retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByBuyerId(Long buyerId) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !order.getBuyerId().equals(buyerId));
        log.info("All orders by buyerId retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !order.getBuyerId().equals(buyerId) && !order.getStatus().equals(status));
        log.info("All orders by status and buyerId retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderBefore(String before) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !LocalDate.parse(order.getDateOfOrder()).isBefore(LocalDate.parse(before)));
        log.info("All orders by date of order before retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfOrderAfter(String after) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> LocalDate.parse(order.getDateOfOrder()).isBefore(LocalDate.parse(after)));
        log.info("All orders by date of order after retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryBefore(String before) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> !LocalDate.parse(order.getDateOfDelivery())
                .isBefore(LocalDate.parse(before)));
        log.info("All orders by date of delivery before retrieved.");
        return newPurchaseOrders;
    }

    @Override
    public List<PurchaseOrder> getAllOrdersByDateOfDeliveryAfter(String after) throws DatabaseException {
        List<PurchaseOrder> newPurchaseOrders = new java.util.ArrayList<>(orders.values().stream().toList());
        newPurchaseOrders.removeIf(order -> LocalDate.parse(order.getDateOfDelivery())
                .isBefore(LocalDate.parse(after)));
        log.info("All orders by date of delivery after retrieved.");
        return newPurchaseOrders;
    }


    @Override
    public Long addOrder(PurchaseOrder purchaseOrder) {
        Long id = (long) idCounter.incrementAndGet();
        log.info(String.valueOf(id));
        purchaseOrder.setId(id);
        orders.put(id, purchaseOrder);
        return id;
    }

}
