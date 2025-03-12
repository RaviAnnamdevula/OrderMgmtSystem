package com.jocata.oms.datamodel.um.form;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderRequest {
    private Integer customerId;
//    private Integer productId;
//    private Integer quantity;
    private List<OrderItemRequest> orderItemRequests;
}
