package com.flab.fpay.api.pay.approve;

import com.flab.fpay.api.pay.service.PayInfoService;
import com.flab.fpay.api.pay.service.PaymentTypeInfoService;
import com.flab.fpay.common.pay.PayInfo;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveMoney {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PayInfoService payInfoService;
    private final PaymentTypeInfoService paymentTypeInfoService;

    public Payment approve(PaymentReady paymentReady) {
//      결제 승인
//      1. 사용자 정보 조회 ( 결제를 위한 정보 조회 )
//      2. 결제 수단 조회 ( money/card )
//      3. 해당 결제 정보에 맞게 금액차감이 되어야함 ( payinfo )
        logger.info("paymentReady :::: " + paymentReady.toString());

        return Optional.of(paymentReady)
            .map(PaymentReady::getUid)
            .map(paymentTypeInfoService::getPaymentTypeInfoByUid)
            .map(paymentTypeInfo -> payInfoService.getPayInfoByPaymentTypeInfoId(
                paymentTypeInfo.getPaymentTypeInfoId()))
            .map(payInfo -> {
                if (payInfo.getMoney().compareTo(paymentReady.getPaymentPrice()) < 0) {
                    throw new RuntimeException("Payment amount exceeds available funds.");
                }
                payInfo.setMoney(payInfo.getMoney().subtract(paymentReady.getPaymentPrice()));
                payInfoService.savePayInfo(payInfo);

                return paymentReady.toPayment(payInfo.getPaymentTypeInfoId());
            })
            .orElseThrow(() -> new RuntimeException("Payment information not found."));
    }

}
