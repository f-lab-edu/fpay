package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.PaymentReady;
import com.flab.fpay.common.pay.PaymentTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResDTO extends BaseResponseDTO{

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

    public PaymentTransactionResDTO(PaymentTransaction paymentTransaction, PaymentReady paymentReady){
        this.paymentId = paymentTransaction.getPaymentReadyId();
        this.companyId = paymentTransaction.getPaymentCompany();
        this.companyOrderNumber = paymentTransaction.getCompanyOrderNumber();
        this.companyUserId = paymentTransaction.getCompanyUserId();
        this.paymentType = paymentReady.getPaymentType();
        this.productPrice = paymentTransaction.getPaymentPrice();
        this.productName = paymentReady.getProductName();
        this.productCount = paymentReady.getProductCount();
        this.createAt = LocalDateTime.now();
        this.paymentAt = paymentTransaction.getPaymentAt();
    }

}
