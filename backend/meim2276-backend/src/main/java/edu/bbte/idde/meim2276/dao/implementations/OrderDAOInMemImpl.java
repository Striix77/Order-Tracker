package edu.bbte.idde.meim2276.dao.implementations;

import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderDAOInMemImpl implements OrderDAOInterface {
    private final transient AtomicInteger idCounter = new AtomicInteger();
    private final transient Map<Integer, Order> orders;
    private static final Logger log = LoggerFactory.getLogger(OrderDAOInMemImpl.class);

    public OrderDAOInMemImpl(Map<Integer, Order> orders) {
        this.orders = orders;
    }

    public OrderDAOInMemImpl() {
        this.orders = new HashMap<>();
    }

    @Override
    public void deleteOrder(int id) {
        log.info("Order deleted: {}", id);
        orders.remove(id);
    }

    @Override
    public void updateOrder(Order order) {
        int id = order.getId();
        orders.remove(id);
        orders.put(id, order);
        log.info("Order updated: {}", id);
        log.info("{}", orders.get(id).getBuyerId());
        log.info(orders.get(id).getDateOfOrder());
        log.info(orders.get(id).getDateOfDelivery());
        log.info(orders.get(id).getStatus());
        log.info("{}", orders.get(id).getTotal());
    }

    @Override
    public Order getOrder(int id) {
        for (Order order : orders.values()) {
            if (order.getId() == id) {
                log.info("Order retrieved: {}", id);
                return order;
            }
        }
        log.info("Order with id {} not found!", id);
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("All orders retrieved.");
        return orders.values().stream().toList();
    }

    @Override
    public void addOrder(Order order) {
        int id = idCounter.incrementAndGet();
        log.info(String.valueOf(id));
        if (order.getId() == 0) {
            order.setId(id);
        }
        orders.put(id,
                new Order(id, order.getBuyerId(), order.getDateOfOrder(), order.getDateOfDelivery(), order.getStatus(),
                        order.getTotal()));
    }

}
