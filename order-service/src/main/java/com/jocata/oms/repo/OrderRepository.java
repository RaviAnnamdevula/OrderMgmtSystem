package com.jocata.oms.repo;

import com.jocata.oms.datamodel.um.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
