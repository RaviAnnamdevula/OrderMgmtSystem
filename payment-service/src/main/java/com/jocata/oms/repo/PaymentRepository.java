package com.jocata.oms.repo;



import com.jocata.oms.datamodel.um.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    List<PaymentEntity> findByOrderId(Integer orderId);
}
