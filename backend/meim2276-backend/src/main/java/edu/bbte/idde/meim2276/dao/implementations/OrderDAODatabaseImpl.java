package edu.bbte.idde.meim2276.dao.implementations;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.dao.factories.ConnectionPoolFactory;
import edu.bbte.idde.meim2276.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAODatabaseImpl implements OrderDAOInterface {
    private static final Logger log = LoggerFactory.getLogger(OrderDAODatabaseImpl.class);
    private final transient HikariDataSource hikariDataSource;


    public OrderDAODatabaseImpl() {
        hikariDataSource = ConnectionPoolFactory.getDataSource();

    }


    @Override
    public void deleteOrder(int id) throws DatabaseException {
        try (Connection con = hikariDataSource.getConnection()) {

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM orders WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            log.info("Order deleted: {}", id);
        } catch (SQLException e) {
            throw new DatabaseException("Database Exception: ", e);
        }
    }

    @Override
    public void updateOrder(Order order) throws DatabaseException {
        try (Connection con = hikariDataSource.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("UPDATE orders SET buyer_id = ?, "
                    + "date_of_order = ?, date_of_delivery = ?, status = ?, total = ? WHERE id = ?");
            pstmt.setInt(1, order.getBuyerId());
            pstmt.setString(2, order.getDateOfOrder());
            pstmt.setString(3, order.getDateOfDelivery());
            pstmt.setString(4, order.getStatus());
            pstmt.setDouble(5, order.getTotal());
            pstmt.setInt(6, order.getId());
            pstmt.executeUpdate();
            int id = order.getId();
            log.info("Order updated: {}", id);
            log.info("{}", order.getBuyerId());
            log.info(order.getDateOfOrder());
            log.info(order.getDateOfDelivery());
            log.info(order.getStatus());
            log.info("{}", order.getTotal());
        } catch (SQLException e) {
            log.error("e: ", e);
            throw new DatabaseException("Database Exception: ", e);
        }
    }

    @Override
    public Order getOrder(int id) throws DatabaseException {
        try (Connection con = hikariDataSource.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM orders WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            if (resultSet.next()) {
                log.info("Order retrieved: {}", id);
                return createNewOrderFromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            log.error("e: ", e);
            throw new DatabaseException("Database Exception: ", e);
        }
    }

    @Override
    public List<Order> getAllOrders() throws DatabaseException {
        log.info("All orders retrieved.");
        try (Connection con = hikariDataSource.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM orders");
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = createNewOrderFromResultSet(resultSet);
                orderList.add(order);
            }
            log.info("Retrieved orders:\n");
            for (Order order : orderList) {
                log.info("{}", order.getBuyerId());
                log.info(order.getDateOfOrder());
                log.info(order.getDateOfDelivery());
                log.info(order.getStatus());
                log.info("{}", order.getTotal());
                log.info("--------------------");
            }
            return orderList;

        } catch (SQLException e) {
            log.error("e: ", e);
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void addOrder(Order order) throws DatabaseException {
        try (Connection con = hikariDataSource.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO orders "
                    + "(buyer_id, date_of_order, date_of_delivery, status, total) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, order.getBuyerId());
            pstmt.setString(2, order.getDateOfOrder());
            pstmt.setString(3, order.getDateOfDelivery());
            pstmt.setString(4, order.getStatus());
            pstmt.setDouble(5, order.getTotal());
            pstmt.executeUpdate();
            log.info("Added order: {}", order.getId());
        } catch (SQLException e) {
            log.error("e: ", e);
            throw new DatabaseException("Database Exception: ", e);
        }

    }

    private Order createNewOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getInt("buyer_id"),
                resultSet.getString("date_of_order"),
                resultSet.getString("date_of_delivery"),
                resultSet.getString("status"),
                resultSet.getDouble("total")
        );
    }

}

