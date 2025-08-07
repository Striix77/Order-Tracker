package edu.bbte.idde.meim2276.backend.controllers;

import edu.bbte.idde.meim2276.backend.business.interfaces.OrderDTOServiceInterface;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderCreateDTO;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderResponseDTO;
import edu.bbte.idde.meim2276.backend.dao.interfaces.UserRepository;
import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final transient Logger logger = org.slf4j.LoggerFactory.getLogger(OrderController.class);
    private final transient OrderDTOServiceInterface orderDTOService;
    private final transient UserRepository userRepository;

    @Autowired
    public OrderController(OrderDTOServiceInterface orderDTOService, UserRepository userRepository) {
        this.orderDTOService = orderDTOService;
        this.userRepository = userRepository;
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.getID(userDetails.getUsername());
    }

    @GetMapping("")
    public List<OrderResponseDTO> listOrders(@RequestParam(required = false) String status) throws DatabaseException {
        Long userId = getAuthenticatedUserId();
        if (!userRepository.isAdmin(userRepository.getUsername(userId))) {
            if (status != null) {
                logger.info("List orders by status requested");
                return orderDTOService.getAllOrdersByStatusAndBuyerId(status, userId);
            }
            logger.info("List orders requested");
            return orderDTOService.getAllOrdersByBuyerId(userId);
        }
        if (status != null) {
            logger.info("List orders by status requested");
            return orderDTOService.getAllOrdersByStatus(status);
        }
        logger.info("List orders requested");
        return orderDTOService.getAllOrders();

    }

    @GetMapping("/order/{id}")
    public OrderResponseDTO getOrder(@PathVariable Long id) throws DatabaseException,
            BadArgumentException, OrderNotFoundException, BadConnectionException {
        logger.info("Get order requested");
        return orderDTOService.getOrder(id);
    }


    @PostMapping("/order/")
    public Long addOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO) throws
            BadArgumentException, DatabaseException {
        logger.info("Add order requested");
        return orderDTOService.addOrder(orderCreateDTO);
    }

    @PutMapping("/order/{id}")
    public void updateOrder(@PathVariable Long id, @RequestBody @Valid OrderCreateDTO orderCreateDTO)
            throws BadArgumentException, OrderNotFoundException, BadConnectionException, DatabaseException {
        logger.info("Update order requested for id: {}", id);
        orderDTOService.updateOrder(orderCreateDTO, id);
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrder(@PathVariable Long id) throws BadArgumentException,
            OrderNotFoundException, BadConnectionException, DatabaseException {
        logger.info("Delete order requested for id: {}", id);
        orderDTOService.deleteOrder(id);
    }


    @GetMapping("/order/search-by-name/{name}")
    public List<OrderResponseDTO> searchByName(@PathVariable String name) throws DatabaseException {
        logger.info("Search orders by name requested");
        return orderDTOService.getAllOrdersByName(name);
    }

    @GetMapping("/order/search-by-date-of-order-before/{before}")
    public List<OrderResponseDTO> searchByDateOfOrderBefore(@PathVariable String before) throws DatabaseException {
        logger.info("Search orders by date of order before... requested");
        return orderDTOService.getAllOrdersByDateOfOrderBefore(before);
    }

    @GetMapping("/order/search-by-date-of-order-after/{after}")
    public List<OrderResponseDTO> searchByDateOfOrderAfter(@PathVariable String after) throws DatabaseException {
        logger.info("Search orders by date of order after... requested");
        return orderDTOService.getAllOrdersByDateOfOrderAfter(after);
    }

    @GetMapping("/order/search-by-date-of-delivery-before/{before}")
    public List<OrderResponseDTO> searchByDateOfDeliveryBefore(@PathVariable String before) throws DatabaseException {
        logger.info("Search orders by date of delivery before... requested");
        return orderDTOService.getAllOrdersByDateOfDeliveryBefore(before);
    }

    @GetMapping("/order/search-by-date-of-delivery-after/{after}")
    public List<OrderResponseDTO> searchByDateOfDeliveryAfter(@PathVariable String after) throws DatabaseException {
        logger.info("Search orders by date of delivery after... requested");
        return orderDTOService.getAllOrdersByDateOfDeliveryAfter(after);
    }
}