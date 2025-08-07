package org.edu.bbte.idde.meim2276.servlets;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LogInServlet.class);
    private transient Template template;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Handlebars handlebars = new Handlebars();
            template = handlebars.compile("templates/login");
        } catch (IOException e) {
            LOG.error("Error compiling login template", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        LOG.info("Request GET arrived to login servlet");
        template.apply(null, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("Request POST arrived to login servlet");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userName = "admin";
        String passWord = "adminPassword";
        if (username.equals(userName) && password.equals(passWord)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            try {
                resp.sendRedirect("orders");
            } catch (IOException e) {
                LOG.error("Error redirecting to orders page", e);
            }
        } else {
            try {
                Map<String, String> model = new ConcurrentHashMap<>();
                model.put("error", "Invalid username or password!");
                template.apply(model, resp.getWriter());
            } catch (IOException e) {
                LOG.error("Error applying template to login page:", e);
            }
        }
    }
}
