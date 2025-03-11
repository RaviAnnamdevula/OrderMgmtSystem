package com.jocata.oms.controller;

import com.jocata.oms.datamodel.um.entity.OrderEntity;
import com.jocata.oms.datamodel.um.form.OrderRequest;
import com.jocata.oms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderEntity> placeOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    @PutMapping("/confirm")
    public ResponseEntity<OrderEntity> confirmOrder(@RequestParam Integer orderId ,@RequestParam String orderStatus ) {
        return ResponseEntity.ok(orderService.confirmOrder(orderId , orderStatus));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrderById(@RequestParam Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
