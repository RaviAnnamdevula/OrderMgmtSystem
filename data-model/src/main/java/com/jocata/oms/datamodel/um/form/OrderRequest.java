package com.jocata.oms.datamodel.um.form;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderRequest {
    private Integer customerId;
    private Integer productId;
    private Integer quantity;
}
