package edu.bbte.idde.meim2276.backend.dao.repositories;

import edu.bbte.idde.meim2276.backend.dao.datatypes.PurchaseOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByStatus(String status);

    List<PurchaseOrder> findByBuyerId(Long buyerId);

    List<PurchaseOrder> findByStatusAndBuyerId(String status, Long buyerId);

    List<PurchaseOrder> findByNameContainingIgnoreCase(String name);

    List<PurchaseOrder> findByDateOfOrder(String dateOfOrder);

    List<PurchaseOrder> findByDateOfDelivery(String dateOfDelivery);

    List<PurchaseOrder> findByTotal(double total);

    List<PurchaseOrder> findByDateOfOrderBefore(String before);

    List<PurchaseOrder> findByDateOfOrderAfter(String after);

    List<PurchaseOrder> findByDateOfDeliveryBefore(String before);

    List<PurchaseOrder> findByDateOfDeliveryAfter(String after);


    @Modifying
    @Transactional
    @Query("DELETE FROM PurchaseOrder po WHERE po.buyerId= :userId")
    void deleteOrdersByUserId(@Param("userId") Long userId);
}
