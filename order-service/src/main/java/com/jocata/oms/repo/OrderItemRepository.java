package com.jocata.oms.repo;

import com.jocata.oms.datamodel.um.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    @Query("SELECT oi FROM OrderItemEntity oi WHERE oi.order.id = :orderId")
    List<OrderItemEntity> findByOrderId(@Param("orderId") Integer orderId);

}
