package edu.bbte.idde.meim2276.backend.controllers.dto;


import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCreateDTO {
    @NotNull
    private Long buyerId;

    @NotNull
    @Size(min = 1, max = 255)
    private String dateOfOrder;

    @NotNull
    @Size(min = 1, max = 255)
    private String dateOfDelivery;

    @NotNull
    @Size(min = 1, max = 255)
    private String status;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private double total;

}