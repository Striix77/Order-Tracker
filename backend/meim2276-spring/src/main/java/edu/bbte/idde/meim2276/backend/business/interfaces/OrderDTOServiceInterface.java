package edu.bbte.idde.meim2276.backend.business.interfaces;

import edu.bbte.idde.meim2276.backend.controllers.dto.OrderCreateDTO;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderResponseDTO;
import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderDTOServiceInterface {
    Long addOrder(OrderCreateDTO orderDTO) throws BadArgumentException, DatabaseException;

    void deleteOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    void updateOrder(OrderCreateDTO order, Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    OrderResponseDTO getOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            DatabaseException, BadConnectionException;

    List<OrderResponseDTO> getAllOrders() throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByStatus(String status) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByName(String name) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByBuyerId(Long buyerId) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByDateOfOrderBefore(String before) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByDateOfOrderAfter(String after) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByDateOfDeliveryBefore(String before) throws DatabaseException;

    List<OrderResponseDTO> getAllOrdersByDateOfDeliveryAfter(String after) throws DatabaseException;

}
