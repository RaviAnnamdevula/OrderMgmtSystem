package com.jocata.oms.datamodel.um.form;

import com.jocata.oms.datamodel.um.entity.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderResponseForm {
    private OrderEntity orderDetails;
    private List<OrderItemEntity> orderItems;
    private List<ProductEntity> products;
 //   private AddressEntity addressEntity;
    private UserEntity userDetails;
}
