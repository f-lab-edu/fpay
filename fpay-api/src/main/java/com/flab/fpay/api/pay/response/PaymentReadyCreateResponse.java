package com.flab.fpay.api.pay.response;

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
public class PaymentReadyCreateResponse {

    private BigInteger paymentRequestId; // 결제 요청 번호

    private String redirectURL; // 결제 페이지

    private LocalDateTime createdAt;

}
