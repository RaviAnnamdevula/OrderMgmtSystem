package com.jocata.oms.service;
import com.jocata.oms.datamodel.um.entity.PaymentEntity;
import com.jocata.oms.repo.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentEntity processPayment(PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    public List<PaymentEntity> getPaymentsByOrderId(Integer orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public PaymentEntity updatePaymentStatus(Integer id, PaymentEntity payment) {
        payment.setPaymentId(id);
        return paymentRepository.save(payment);
    }
}