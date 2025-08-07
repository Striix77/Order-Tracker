package edu.bbte.idde.meim2276.backend.business.services;

import edu.bbte.idde.meim2276.backend.business.interfaces.OrderDTOServiceInterface;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderResponseDTO;
import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderCreateDTO;
import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import edu.bbte.idde.meim2276.backend.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderDTOService implements OrderDTOServiceInterface {
    private final transient OrderService orderService;
    private final transient OrderMapper orderMapper;

    @Autowired
    public OrderDTOService(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public Long addOrder(OrderCreateDTO orderCreateDTO) throws BadArgumentException, DatabaseException {
        PurchaseOrder purchaseOrder = orderMapper.fromCreatetoEntity(orderCreateDTO);
        return orderService.addOrder(purchaseOrder);
    }

    @Override
    public void deleteOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            BadConnectionException, DatabaseException {
        orderService.deleteOrder(id);
    }

    @Override
    public void updateOrder(OrderCreateDTO orderCreateDTO, Long id) throws BadArgumentException, OrderNotFoundException,
            BadConnectionException, DatabaseException {
        PurchaseOrder purchaseOrder = orderMapper.fromCreatetoEntity(orderCreateDTO);
        orderService.updateOrder(purchaseOrder, id);
    }

    @Override
    public OrderResponseDTO getOrder(Long id) throws BadArgumentException, OrderNotFoundException,
            BadConnectionException, DatabaseException {
        PurchaseOrder purchaseOrder = orderService.getOrder(id);
        return orderMapper.toResponseDTO(purchaseOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrders();
        log.info("NAME{}", purchaseOrders.get(0).getName());
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByStatus(String status) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByStatus(status);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByName(String name) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByName(name);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByBuyerId(Long buyerId) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByBuyerId(buyerId);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByStatusAndBuyerId(String status, Long buyerId) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByStatusAndBuyerId(status, buyerId);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByDateOfOrderBefore(String before) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByDateOfOrderBefore(before);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByDateOfOrderAfter(String after) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByDateOfOrderAfter(after);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByDateOfDeliveryBefore(String before) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByDateOfDeliveryBefore(before);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByDateOfDeliveryAfter(String after) throws DatabaseException {
        List<PurchaseOrder> purchaseOrders = orderService.getAllOrdersByDateOfDeliveryAfter(after);
        return purchaseOrders.stream()
                .map(orderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


}