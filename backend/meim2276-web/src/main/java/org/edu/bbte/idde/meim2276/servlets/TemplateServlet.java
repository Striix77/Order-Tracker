package org.edu.bbte.idde.meim2276.servlets;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/orders")
public class TemplateServlet extends HttpServlet {
    private final transient Logger log = LoggerFactory.getLogger(TemplateServlet.class);
    private transient OrderServiceInterface orderService;
    private transient Handlebars handlebars;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log.info("Initializing example servlet");
        orderService = new OrderServiceFactory().createOrderService();
        handlebars = new Handlebars();
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("Destroying example servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        log.info("Request GET arrived to example servlet");
        String idParam = req.getParameter("id");

        try {
            resp.setContentType("text/html");
            Template template = handlebars.compile("templates/order");
            Map<String, Object> model = new ConcurrentHashMap<>();
            if (idParam != null) {
                log.info("Getting order by id: {}", idParam);
                Order order = orderService.getOrder(Integer.parseInt(idParam));
                model.put("order", order);
            } else {
                model.put("orders", orderService.getAllOrders());
            }
            template.apply(model, resp.getWriter());
        } catch (BadConnectionException | BadArgumentException | OrderNotFoundException e) {
            log.error("Error while getting order by id:", e);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (DatabaseException e) {
            log.error("Error while getting order by id:", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
