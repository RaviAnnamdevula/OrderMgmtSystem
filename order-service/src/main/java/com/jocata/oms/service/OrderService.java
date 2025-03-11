package com.jocata.oms.service;


import com.jocata.oms.datamodel.um.entity.OrderEntity;
import com.jocata.oms.datamodel.um.entity.OrderItemEntity;
import com.jocata.oms.datamodel.um.entity.ProductEntity;
import com.jocata.oms.datamodel.um.form.OrderRequest;
import com.jocata.oms.repo.OrderItemRepository;
import com.jocata.oms.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    public OrderEntity placeOrder(OrderRequest orderRequest) {
        // Fetch product details from Product Service
        ProductEntity product = restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + orderRequest.getProductId(), ProductEntity.class);

        // Reserve stock via Inventory Service
        restTemplate.put("http://INVENTORY-SERVICE/inventory/reserve?productId=" + orderRequest.getProductId() + "&quantity=" + orderRequest.getQuantity(), null);

        OrderEntity order = new OrderEntity();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderStatus(OrderEntity.OrderStatus.valueOf("PENDING"));
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity())));
        order = orderRepository.save(order);

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderItemId(order.getOrderId());
        orderItem.setProductId(orderRequest.getProductId());
        orderItem.setQuantity(orderRequest.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItemRepository.save(orderItem);

        return order;
    }

    public OrderEntity confirmOrder(Integer orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderEntity.OrderStatus.valueOf("CONFIRMED"));
        return orderRepository.save(order);
    }

    public OrderEntity getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}