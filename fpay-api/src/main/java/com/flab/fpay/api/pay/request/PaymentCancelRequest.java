package com.flab.fpay.api.pay.request;

import java.math.BigDecimal;
import java.math.BigInteger;
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
public class PaymentCancelRequest {

    private BigInteger paymentId; // 결제 ID

    private BigInteger paymentCompany; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal paymentPrice; // 취소 금액 ( 총액 )

    private BigInteger uid;

}
