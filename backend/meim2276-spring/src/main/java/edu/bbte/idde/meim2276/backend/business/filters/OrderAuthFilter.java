package edu.bbte.idde.meim2276.backend.business.filters;

import edu.bbte.idde.meim2276.backend.dao.interfaces.OrderDAOInterface;
import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OrderAuthFilter extends OncePerRequestFilter {

    private final transient OrderDAOInterface orderDAO;
    private final transient UserRepository userRepository;

    @Autowired
    public OrderAuthFilter(OrderDAOInterface orderDAO, UserRepository userRepository) {
        super();
        this.orderDAO = orderDAO;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Pattern pattern = Pattern.compile("/order/(\\d+)$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            String orderIdStr = matcher.group(1);
            logger.info("Order ID: " + orderIdStr);
            try {
                Long orderId = Long.parseLong(orderIdStr);
                PurchaseOrder order = orderDAO.getOrder(orderId);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                Long userId = userRepository.getID(userDetails.getUsername());
                if (order != null && !order.getBuyerId().equals(userId) && !userRepository
                        .isAdmin(userRepository.getUsername(userId))) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;

                }
            } catch (NumberFormatException | DatabaseException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}