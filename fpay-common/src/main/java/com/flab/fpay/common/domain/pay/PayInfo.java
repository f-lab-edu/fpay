package com.flab.fpay.common.domain.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "pay_info")
@Getter
@Setter
@ToString
public class PayInfo {

    @Id
    @Column(name = "pay_info_id")
    private BigInteger payInfoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PaymentTypeInfo paymentTypeInfo;

    @Column(name = "money")
    private BigDecimal money;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
