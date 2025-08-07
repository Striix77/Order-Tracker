package edu.bbte.idde.meim2276.backend.mapper;

import edu.bbte.idde.meim2276.backend.controllers.dto.OrderResponseDTO;
import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import edu.bbte.idde.meim2276.backend.controllers.dto.OrderCreateDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderCreateDTO toCreateDTO(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            return null;
        }
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setName(purchaseOrder.getName());
        orderCreateDTO.setBuyerId(purchaseOrder.getBuyerId());
        orderCreateDTO.setDateOfOrder(purchaseOrder.getDateOfOrder());
        orderCreateDTO.setDateOfDelivery(purchaseOrder.getDateOfDelivery());
        orderCreateDTO.setStatus(purchaseOrder.getStatus());
        orderCreateDTO.setTotal(purchaseOrder.getTotal());
        return orderCreateDTO;
    }

    public OrderResponseDTO toResponseDTO(PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null) {
            return null;
        }
        OrderResponseDTO orderCreateDTO = new OrderResponseDTO();
        orderCreateDTO.setId(purchaseOrder.getId());
        orderCreateDTO.setName(purchaseOrder.getName());
        orderCreateDTO.setBuyerId(purchaseOrder.getBuyerId());
        orderCreateDTO.setDateOfOrder(purchaseOrder.getDateOfOrder());
        orderCreateDTO.setDateOfDelivery(purchaseOrder.getDateOfDelivery());
        orderCreateDTO.setStatus(purchaseOrder.getStatus());
        orderCreateDTO.setTotal(purchaseOrder.getTotal());
        return orderCreateDTO;
    }

    public PurchaseOrder fromCreatetoEntity(OrderCreateDTO orderCreateDTO) {
        if (orderCreateDTO == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setName(orderCreateDTO.getName());
        purchaseOrder.setBuyerId(orderCreateDTO.getBuyerId());
        purchaseOrder.setDateOfOrder(orderCreateDTO.getDateOfOrder());
        purchaseOrder.setDateOfDelivery(orderCreateDTO.getDateOfDelivery());
        purchaseOrder.setStatus(orderCreateDTO.getStatus());
        purchaseOrder.setTotal(orderCreateDTO.getTotal());
        return purchaseOrder;
    }

    public PurchaseOrder fromResponsetoEntity(OrderResponseDTO orderCreateDTO) {
        if (orderCreateDTO == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(orderCreateDTO.getId());
        purchaseOrder.setName(orderCreateDTO.getName());
        purchaseOrder.setBuyerId(orderCreateDTO.getBuyerId());
        purchaseOrder.setDateOfOrder(orderCreateDTO.getDateOfOrder());
        purchaseOrder.setDateOfDelivery(orderCreateDTO.getDateOfDelivery());
        purchaseOrder.setStatus(orderCreateDTO.getStatus());
        purchaseOrder.setTotal(orderCreateDTO.getTotal());
        return purchaseOrder;
    }
}