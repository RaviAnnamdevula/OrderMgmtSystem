package com.jocata.oms.datamodel.um.form;

import com.jocata.oms.datamodel.um.entity.PaymentMethod;
import com.jocata.oms.datamodel.um.entity.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Integer orderId;
    private String paymentMethod;
    private String amount;
    private String paymentStatus;
}
