package com.flab.fpay.common.domain.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_request")
@Getter
@Setter
@ToString
public class PaymentRequest {

    @Id
    @Column(name = "payment_request_id")
    private BigInteger paymentRequestId;

    @OneToOne
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Company company;

    @Column(name = "product_id")
    private BigInteger productId;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @Column(name = "request_at")
    private LocalDateTime requestAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
