package com.flab.fpay.api.pay.dto;

import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private String productName; // 주문 상품명

    private Integer productCount; // 주문 수량

    private BigDecimal productPrice; // 상품 금액 ( 총액 )

    private String paymentType; // 결제 타입 ( CRAD/MONEY )

    private LocalDateTime requestAt; // 결제 요청 시각 ( 가맹점 기준 )

    public PaymentRequest toEntity(Company company) {
        return PaymentRequest.builder()
                .companyId(company.getCompanyId())
                .companyOrderNumber(this.companyOrderNumber)
                .companyUserId(this.companyUserId)
                .productName(this.productName)
                .productCount(this.productCount)
                .paymentPrice(this.productPrice)
                .paymentType(this.paymentType)
                .requestAt(this.requestAt)
                .build();
    }

}
