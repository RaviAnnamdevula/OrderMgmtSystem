package com.jocata.oms.repo;

import com.jocata.oms.datamodel.um.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    OrderItemEntity findByOrderId(Integer orderId);
}
