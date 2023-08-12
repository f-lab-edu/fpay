package com.flab.fpay.common.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "card_payment_request")
@Getter
@Setter
@ToString
public class CardPaymentRequest implements Serializable {

    @Id
    @Column(name = "card_payment_request_id")
    private BigInteger cardPaymentRequestId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_transaction_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private PaymentTransaction paymentTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_info_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CardInfo cardInfo;

    @Column(name = "approve_id")
    private String approveId;

    @Column(name = "card_req_status")
    private int cardReqStatus;

    @Column(name = "payment_req_at")
    private LocalDateTime paymentReqAt;

    @Column(name = "approve_at")
    private LocalDateTime approveAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
