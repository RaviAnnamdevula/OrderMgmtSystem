package com.jocata.oms.service;
import com.jocata.oms.datamodel.um.entity.PaymentEntity;
import com.jocata.oms.datamodel.um.entity.PaymentMethod;
import com.jocata.oms.datamodel.um.entity.PaymentStatus;
import com.jocata.oms.datamodel.um.form.PaymentRequest;
import com.jocata.oms.repo.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private RestTemplate restTemplate = new RestTemplate();

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;

    }

    public PaymentEntity processPayment(PaymentRequest paymentRequest) {
        // Generate a random transaction ID
        String transactionId = UUID.randomUUID().toString();

        // Create PaymentEntity
        PaymentEntity payment = PaymentEntity.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()))
                .paymentStatus(PaymentStatus.valueOf(paymentRequest.getPaymentStatus()))
                .transactionId(transactionId)
                .amount(new BigDecimal(paymentRequest.getAmount()))
                .build();



        PaymentEntity paymentEntity = paymentRepository.save(payment);
        if(paymentRequest.getPaymentStatus() == "COMPLETED"){
            updatePaymentStatus(payment.getPaymentId(),paymentRequest);
        }
        return paymentEntity;
    }

    public List<PaymentEntity> getPaymentsByOrderId(Integer orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public PaymentEntity updatePaymentStatus(Integer paymentId, PaymentRequest paymentRequest) {
        PaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentStatus status = PaymentStatus.valueOf(paymentRequest.getPaymentStatus());
        payment.setPaymentStatus(status);
       // payment.setUpdatedAt(Timestamp.from(Instant.now()));

        // Update order status and payment status if COMPLETED
        if (status == PaymentStatus.COMPLETED) {
           // restTemplate.put("http://localhost:8083/order/orders/" + payment.getOrderId()+"/confirm", "{\"order_status\": \"CONFIRMED\", \"is_paid\": true}");
            restTemplate.put("http://localhost:8083/order/orders/confirm?orderId=" + payment.getOrderId()+"&orderStatus=CONFIRMED", null);
           /* restTemplate.put("http://localhost:8082/inventory/warehouse/reserve?productId=" + paymentRequest.ge() + "&quantity=" + orderRequest.getQuantity(), null);*/
            //  restTemplate.put("http://localhost:8082/inventory/update", "{\"product_id\": 1, \"reserved_stock\": 0}");
        }

        return paymentRepository.save(payment);
    }
}