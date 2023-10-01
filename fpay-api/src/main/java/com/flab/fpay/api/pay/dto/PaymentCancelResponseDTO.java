package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.PaymentTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentCancelResponseDTO extends BaseResponseDTO {

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal cancelPrice; // 취소 요청 금액 ( 총액 )

    private BigDecimal remainingPrice;

    private LocalDateTime createAt;

    private LocalDateTime cancelAt;

    public PaymentCancelResponseDTO(PaymentTransaction paymentTransaction, PaymentCancelRequestDTO paymentCancelRequestDTO){
        this.paymentId = paymentTransaction.getPaymentRequestId();
        this.companyId = paymentTransaction.getPaymentCompany();
        this.companyOrderNumber = paymentTransaction.getCompanyOrderNumber();
        this.companyUserId = paymentTransaction.getCompanyUserId();
        this.cancelPrice = paymentCancelRequestDTO.getProductPrice();
        this.remainingPrice = paymentTransaction.getPaymentPrice();
        this.createAt = LocalDateTime.now();
        this.cancelAt = paymentTransaction.getPaymentAt();
    }

}
