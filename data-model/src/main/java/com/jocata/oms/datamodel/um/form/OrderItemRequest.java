package com.jocata.oms.datamodel.um.form;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemRequest {

    private Integer productId;

    private Integer quantity;

    private BigDecimal price;
}
