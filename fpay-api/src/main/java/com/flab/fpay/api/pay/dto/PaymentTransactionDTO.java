package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.PayInfo;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionDTO {

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal productPrice; // 상품 금액 ( 총액 )

    private BigInteger uid;

    public PaymentTransaction toEntity(PaymentRequest paymentRequest, PayInfo payInfo){
        return PaymentTransaction.builder()
                .paymentPrice(paymentRequest.getPaymentPrice())
                .paymentCompany(paymentRequest.getCompanyId())
                .companyUserId(this.companyUserId)
                .companyOrderNumber(this.companyOrderNumber)
                .uid(this.uid)
                .paymentTypeInfoId(payInfo.getPaymentTypeInfoId())
                .paymentRequestId(paymentRequest.getPaymentRequestId())
                .paymentAt(LocalDateTime.now())
                .build();
    }
}
