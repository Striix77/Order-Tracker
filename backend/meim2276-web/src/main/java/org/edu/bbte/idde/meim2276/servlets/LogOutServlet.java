package org.edu.bbte.idde.meim2276.servlets;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LogOutServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("Request POST arrived to logout servlet");
        HttpSession session = req.getSession();
        if (session != null) {
            session.invalidate();
        }
        try {
            resp.sendRedirect("login");
        } catch (IOException e) {
            LOG.error("Error redirecting to login page", e);
        }
    }
}
