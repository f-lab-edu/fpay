package com.flab.fpay.api.pay.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PaymentReadyResDTO {

    private BigInteger paymentId; // 결제 번호

    private String redirectURL; // 결제 페이지

    private LocalDateTime createAt;

    @Builder
    public PaymentReadyResDTO(BigInteger paymentId, String redirectURL){
        this.paymentId = paymentId;
        this.redirectURL = redirectURL;
        this.createAt = LocalDateTime.now();
    }

}
