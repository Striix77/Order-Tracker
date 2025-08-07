package edu.bbte.idde.meim2276.backend.controllers.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderResponseDTO {

    private Long id;

    private Long buyerId;

    private String name;
    
    private String dateOfOrder;

    private String dateOfDelivery;

    private String status;

    private double total;

}