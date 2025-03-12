package com.jocata.oms.service;

import com.jocata.oms.datamodel.um.entity.OrderEntity;
import com.jocata.oms.datamodel.um.entity.OrderItemEntity;
import com.jocata.oms.datamodel.um.entity.OrderStatus;
import com.jocata.oms.datamodel.um.entity.ProductEntity;
import com.jocata.oms.datamodel.um.form.OrderRequest;
import com.jocata.oms.repo.OrderItemRepository;
import com.jocata.oms.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${product-service-url}")
    private String productServiceUrl;

    @Value("${inventory-service-url}")
    private String inventoryServiceUrl;

    @Transactional(rollbackFor = Exception.class)
    public OrderEntity placeOrder(OrderRequest orderRequest) {
        //1) fetch the product details first
        //2) Save item in to order entity with status PENDING, and add into the orderItem list
        //3) update the reserve stock -quantity +reserveStock
        ProductEntity product = fetchProduct(orderRequest.getProductId());
        OrderEntity order = createOrder(orderRequest, product);
        orderRepository.save(order);
        createOrderItem(order, orderRequest, product);
        reserveStock(orderRequest.getProductId(), orderRequest.getQuantity());
        return order;
    }

    private ProductEntity fetchProduct(Integer productId) {
        return webClientBuilder.build()
                .get()
                .uri(productServiceUrl + "/getByProductId?id=" + productId)
                .retrieve()
                .bodyToMono(ProductEntity.class)
                .block();
    }

    private void reserveStock(Integer productId, int quantity) {
        webClientBuilder.build()
                .put()
                .uri(inventoryServiceUrl + "/reserve?productId=" + productId + "&quantity=" + quantity)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private OrderEntity createOrder(OrderRequest orderRequest, ProductEntity product) {
        OrderEntity order = new OrderEntity();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity())));
        return order;
    }

    private void createOrderItem(OrderEntity order, OrderRequest orderRequest, ProductEntity product) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrder(order);
        orderItem.setProductId(orderRequest.getProductId());
        orderItem.setQuantity(orderRequest.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItemRepository.save(orderItem);
    }

    public OrderEntity confirmOrder(Integer orderId, String orderStatus) {
        // Get the order entity ->
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItemEntity orderItem = orderItemRepository.findByOrderId(orderId);


        if ("CANCELLED".equals(orderStatus)) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            releaseStock(orderItem.getProductId(), orderItem.getQuantity());
        } else if ("CONFIRMED".equals(orderStatus)) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setIsPaid(true);
            updateStock(orderItem.getProductId());
        }



        return orderRepository.save(order);
    }

    private void releaseStock(Integer productId, int quantity) {
        webClientBuilder.build()
                .put()
                .uri(inventoryServiceUrl + "/release?productId=" + productId + "&quantity=" + quantity)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private void updateStock(Integer productId) {
        webClientBuilder.build()
                .put()
                .uri(inventoryServiceUrl + "/updateStock?productId=" + productId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public OrderEntity getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}
/*
package com.jocata.oms.service;


import com.jocata.oms.datamodel.um.entity.OrderEntity;
import com.jocata.oms.datamodel.um.entity.OrderItemEntity;
import com.jocata.oms.datamodel.um.entity.OrderStatus;
import com.jocata.oms.datamodel.um.entity.ProductEntity;
import com.jocata.oms.datamodel.um.form.OrderRequest;
import com.jocata.oms.repo.OrderItemRepository;
import com.jocata.oms.repo.OrderRepository;
import org.hibernate.annotations.TenantId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional(rollbackFor = Exception.class)
    public OrderEntity placeOrder(OrderRequest orderRequest) {
        // Fetch product details from Product Service
        ProductEntity product = restTemplate.getForObject("http://localhost:8085/product/products/getByProductId?id=" + orderRequest.getProductId(), ProductEntity.class);

        // Reserve stock via Inventory Service
        restTemplate.put("http://localhost:8082/inventory/warehouse/reserve?productId=" + orderRequest.getProductId() + "&quantity=" + orderRequest.getQuantity(), null);

        OrderEntity order = new OrderEntity();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity())));
        order = orderRepository.save(order);

        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrder(order);
        orderItem.setProductId(orderRequest.getProductId());
        orderItem.setQuantity(orderRequest.getQuantity());
        orderItem.setPrice(product.getPrice());
        orderItemRepository.save(orderItem);

        return order;
    }

    public OrderEntity confirmOrder(Integer orderId , String orderStatus) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        OrderItemEntity orderItemEntities = orderItemRepository.findByOrderId(orderId);
        restTemplate.put("http://localhost:8082/inventory/warehouse/release?productId=" + orderItemEntities.getProductId() + "&quantity=" + orderItemEntities.getQuantity(), null);
        if(orderStatus == "CANCELLED"){
            order.setOrderStatus(OrderStatus.CANCELLED);
           return orderRepository.save(order);
        }else if(orderStatus == "CONFIRMED"){
            order.setOrderStatus(OrderStatus.valueOf(orderStatus));
            order.setIsPaid(true);
            restTemplate.put("localhost:8082/inventory/warehouse/updateStock?productId="+orderItemEntities.getProductId(),null);
           return orderRepository.save(order);
        }

        return null;
    }

    public OrderEntity getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }
}*/
