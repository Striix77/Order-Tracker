package org.edu.bbte.idde.meim2276.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.meim2276.business.interfaces.OrderServiceInterface;
import edu.bbte.idde.meim2276.dao.datatypes.Order;
import edu.bbte.idde.meim2276.dao.factories.OrderServiceFactory;
import edu.bbte.idde.meim2276.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.exceptions.OrderNotFoundException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/json")
public class Servlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Servlet.class);
    private transient OrderServiceInterface orderService;
    private transient ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Initializing example servlet");
        orderService = new OrderServiceFactory().createOrderService();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("Destroying example servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Request GET arrived to json servlet");
        String idParam = req.getParameter("id");
        resp.setContentType("application/json");

        try {
            if (idParam != null) {
                log.info("Getting order by id: {}", idParam);
                Order order = orderService.getOrder(Integer.parseInt(idParam));
                objectMapper.writeValue(resp.getOutputStream(), order);

            } else {
                objectMapper.writeValue(resp.getOutputStream(), orderService.getAllOrders());
            }
        } catch (NumberFormatException e) {
            log.error("Error while getting order by id:", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        } catch (DatabaseException e) {
            log.error("Error while getting order by id:", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        } catch (BadConnectionException | BadArgumentException | OrderNotFoundException e) {
            log.error("Error while getting order by id:", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Request POST arrived to json servlet");
        try {
            Order order = objectMapper.readValue(req.getInputStream(), Order.class);
            log.info("Creating order: {}", order);
            orderService.addOrder(order);
        } catch (DatabaseException e) {
            log.error("Error while creating order: ", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        } catch (BadArgumentException | IOException e) {
            log.error("Error while creating order: ", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Request DELETE arrived to json servlet");
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                log.info("Deleting order by id: {}", idParam);
                orderService.deleteOrder(Integer.parseInt(idParam));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (BadConnectionException | NumberFormatException | BadArgumentException | DatabaseException e) {
            log.error("Error while updating order:", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        } catch (OrderNotFoundException e) {
            log.error("Error while deleting order:", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Request PUT arrived to json servlet");
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                log.info("Updating order with id: {}", idParam);
                Order order = objectMapper.readValue(req.getInputStream(), Order.class);
                order.setId(Integer.parseInt(idParam));
                orderService.updateOrder(order);

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (BadConnectionException | BadArgumentException | DatabaseException
                 | IOException | NumberFormatException e) {
            log.error("Error while updating order:", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        } catch (OrderNotFoundException e) {
            log.error("Error while updating order:", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getOutputStream(), Collections.singletonMap("error", e.getMessage()));
        }
    }
}