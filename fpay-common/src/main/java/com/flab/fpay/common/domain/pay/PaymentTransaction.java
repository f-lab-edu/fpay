package com.flab.fpay.common.domain.pay;

import com.flab.fpay.common.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_transaction")
@Getter
@Setter
@ToString
public class PaymentTransaction implements Serializable {

    @Id
    @Column(name = "payment_transaction_id")
    private BigInteger paymentTransactionId;

    @Column(name = "payment_company")
    private String paymentCompany;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PaymentTypeInfo paymentTypeInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_request_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PaymentRequest paymentRequest;

    @Column(name = "product_id")
    private BigInteger productId;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "payment_at")
    private LocalDateTime paymentAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
