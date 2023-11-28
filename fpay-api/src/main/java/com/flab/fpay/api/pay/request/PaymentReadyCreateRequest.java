package com.flab.fpay.api.pay.request;

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
public class PaymentReadyCreateRequest {

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private String productName; // 주문 상품명

    private Integer productCount; // 주문 수량

    private BigDecimal paymentPrice; // 상품 금액 ( 총액 )

    private String paymentType; // 결제 타입 ( CRAD/MONEY )

    private LocalDateTime requestAt; // 결제 요청 시각 ( 가맹점 기준 )

}
