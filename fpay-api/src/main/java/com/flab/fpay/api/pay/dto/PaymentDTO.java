package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.PayInfo;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import com.flab.fpay.common.pay.PaymentTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal productPrice; // 상품 금액 ( 총액 )

    private BigInteger uid;

    public Payment toEntity(PaymentReady paymentReady, PayInfo payInfo){
        return Payment.builder()
                .paymentPrice(paymentReady.getPaymentPrice())
                .paymentCompany(paymentReady.getCompanyId())
                .companyUserId(this.companyUserId)
                .companyOrderNumber(this.companyOrderNumber)
                .uid(this.uid)
                .paymentTypeInfoId(payInfo.getPaymentTypeInfoId())
                .paymentReadyId(paymentReady.getPaymentReadyId())
                .paymentAt(LocalDateTime.now())
                .build();
    }
}
