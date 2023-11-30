package com.flab.fpay.common.pay;

import com.flab.fpay.common.BaseTimeEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity(name = "payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private BigInteger paymentId;

    @Column(name = "payment_ready_id")
    private BigInteger paymentReadyId;

    @Column(name = "payment_company")
    private BigInteger paymentCompany;

    @Column(name = "company_order_number")
    private String companyOrderNumber;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @Column(name = "company_user_id")
    private String companyUserId;

    @Column(name = "uid")
    private BigInteger uid;

    @Column(name = "payment_type_info_id")
    private BigInteger paymentTypeInfoId;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "payment_at")
    private LocalDateTime paymentAt;

    public PaymentTransaction toPaymentTransaction(){
        return PaymentTransaction.builder()
            .paymentId(this.paymentId)
            .paymentReadyId(this.paymentReadyId)
            .paymentCompany(this.paymentCompany)
            .companyOrderNumber(this.companyOrderNumber)
            .paymentPrice(this.paymentPrice)
            .companyUserId(this.companyUserId)
            .uid(this.uid)
            .paymentTypeInfoId(this.paymentTypeInfoId)
            .paymentStatus(this.paymentStatus)
            .build();
    }
}
