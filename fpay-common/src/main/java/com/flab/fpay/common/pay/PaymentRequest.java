package com.flab.fpay.common.pay;

import com.flab.fpay.common.BaseTimeEntity;
import com.flab.fpay.common.company.Company;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_request")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_request_id")
    private BigInteger paymentRequestId;

    @Column(name = "company_id")
    private BigInteger companyId;

    @Column(name = "company_order_number")
    private String companyOrderNumber;

    @Column(name = "company_user_id")
    private String companyUserId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

    @Column(name = "product_count")
    private Integer productCount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "request_at")
    private LocalDateTime requestAt;

    public PaymentTransaction toPaymentTransaction(){
        return PaymentTransaction.builder()
            .paymentRequestId(this.paymentRequestId)
            .paymentCompany(this.companyId)
            .companyOrderNumber(this.companyOrderNumber)
            .paymentPrice(this.paymentPrice)
            .companyUserId(this.companyUserId)
            .build();
    }
}
