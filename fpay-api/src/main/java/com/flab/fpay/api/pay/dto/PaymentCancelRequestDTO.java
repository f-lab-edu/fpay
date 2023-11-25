package com.flab.fpay.api.pay.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentCancelRequestDTO extends BaseResponseDTO {

    private BigInteger paymentId; // 결제 ID

    private BigInteger companyId; // 가맹점 ID

    private String companyOrderNumber; // 가맹점 주문번호

    private String companyUserId; // 가맹점 User Id

    private BigDecimal productPrice; // 취소 금액 ( 총액 )

    private BigInteger uid;

}
