package com.flab.fpay.common.card;

import com.flab.fpay.common.BaseTimeEntity;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "card_info")
@Getter
@Setter
@ToString
public class CardInfo extends BaseTimeEntity {

    @Id
    @Column(name = "card_info_id")
    private BigInteger cardInfoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_company_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CardCompany cardCompany;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PaymentTypeInfo paymentTypeInfo;

    @Column(name = "card_number")
    private String cardNumber;

}
