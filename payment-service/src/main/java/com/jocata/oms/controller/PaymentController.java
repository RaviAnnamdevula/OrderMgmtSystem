package com.jocata.oms.controller;


import com.jocata.oms.datamodel.um.entity.PaymentEntity;
import com.jocata.oms.datamodel.um.form.PaymentRequest;
import com.jocata.oms.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentEntity> processPayment(@RequestBody PaymentRequest payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<PaymentEntity>> getPaymentByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaymentsByOrderId(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentEntity> updatePaymentStatus(@PathVariable Integer id, @RequestBody PaymentRequest payment) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(id, payment));
    }
}