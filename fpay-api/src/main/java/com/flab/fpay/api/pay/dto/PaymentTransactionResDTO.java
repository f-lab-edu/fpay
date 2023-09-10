package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResDTO {

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private String paymentType; // 결제 타입

    private BigDecimal productPrice; // 상품 금액 ( 총액 )

    private String productName; // 상품명

    private Integer productCount; // 주문 수량

    private LocalDateTime createAt;

    private LocalDateTime paymentAt;

    public PaymentTransactionResDTO(PaymentTransaction paymentTransaction, PaymentRequest paymentRequest){
        this.paymentId = paymentTransaction.getPaymentRequestId();
        this.companyId = paymentTransaction.getPaymentCompany();
        this.companyOrderNumber = paymentTransaction.getCompanyOrderNumber();
        this.companyUserId = paymentTransaction.getCompanyUserId();
        this.paymentType = paymentRequest.getPaymentType();
        this.productPrice = paymentTransaction.getPaymentPrice();
        this.productName = paymentRequest.getProductName();
        this.productCount = paymentRequest.getProductCount();
        this.createAt = paymentTransaction.getCreatedAt();
        this.paymentAt = paymentTransaction.getPaymentAt();
    }

}
