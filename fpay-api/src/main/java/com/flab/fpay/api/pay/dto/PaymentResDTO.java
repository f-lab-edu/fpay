package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResDTO extends BaseResponseDTO{

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private BigInteger paymentRequestId; // 결제승인 요청 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private String paymentType; // 결제 타입

    private BigDecimal productPrice; // 상품 금액 ( 총액 )

    private String productName; // 상품명

    private Integer productCount; // 주문 수량

    private LocalDateTime createAt;

    private LocalDateTime paymentAt;

    public PaymentResDTO(Payment payment, PaymentRequest paymentRequest){
        this.paymentId = payment.getPaymentId();
        this.companyId = payment.getPaymentCompany();
        this.companyOrderNumber = payment.getCompanyOrderNumber();
        this.companyUserId = payment.getCompanyUserId();
        this.paymentType = paymentRequest.getPaymentType();
        this.productPrice = payment.getPaymentPrice();
        this.productName = paymentRequest.getProductName();
        this.productCount = paymentRequest.getProductCount();
        this.createAt = payment.getCreatedAt();
        this.paymentAt = payment.getPaymentAt();
    }

}
