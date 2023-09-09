package com.flab.fpay.common.pay;

import com.flab.fpay.common.BaseTimeEntity;
import com.flab.fpay.common.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentTransaction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_transaction_id")
    private BigInteger paymentTransactionId;

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

    @Column(name = "payment_request_id")
    private BigInteger paymentRequestId;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "payment_at")
    private LocalDateTime paymentAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
