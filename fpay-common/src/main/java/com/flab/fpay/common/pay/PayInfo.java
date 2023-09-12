package com.flab.fpay.common.pay;

import com.flab.fpay.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "pay_info")
@Getter
@Setter
@ToString
public class PayInfo extends BaseTimeEntity {

    @Id
    @Column(name = "pay_info_id")
    private BigInteger payInfoId;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_type_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private PaymentTypeInfo paymentTypeInfo;

    @Column(name = "payment_type_info_id")
    private BigInteger paymentTypeInfoId;

    @Column(name = "money")
    private BigDecimal money;

}
