package com.jocata.oms.datamodel.um.form;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String name;

    private String description;

    private String price;

    private Integer quantity;
}
