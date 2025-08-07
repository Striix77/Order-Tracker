package edu.bbte.idde.meim2276.backend.dao.datatypes;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseOrder extends BaseEntity {

    private String name;
    private Long buyerId;
    private String dateOfOrder;
    private String dateOfDelivery;
    private String status;
    private double total;


}
