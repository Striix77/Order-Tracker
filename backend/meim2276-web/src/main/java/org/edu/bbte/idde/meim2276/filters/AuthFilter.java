package org.edu.bbte.idde.meim2276.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/orders/*")
public class AuthFilter extends HttpFilter {
    private final transient Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init() {
        log.info("Initializing auth filter...");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("user") == null) {
            log.error("User not logged in, redirecting to login page...");
            resp.sendRedirect("login");
            return;
        }
        chain.doFilter(req, resp);
    }
}
