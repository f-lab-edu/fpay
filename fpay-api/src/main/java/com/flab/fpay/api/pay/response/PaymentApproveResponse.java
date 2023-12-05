package com.flab.fpay.api.pay.response;

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
public class PaymentApproveResponse {

    private BigInteger paymentId; // 결제 ID

    private BigInteger paymentCompany; // 가맹점 ID

    private BigInteger paymentReadyId; // 결제승인 요청 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal paymentPrice; // 상품 금액 ( 총액 )

    private String paymentStatus;

    private LocalDateTime paymentAt;

}
