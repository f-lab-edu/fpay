package com.flab.fpay.api.pay.approve;

import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.service.PayInfoService;
import com.flab.fpay.api.pay.service.PaymentTypeInfoService;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApproveMoney {

    private final PayInfoService payInfoService;
    private final PaymentTypeInfoService paymentTypeInfoService;

    public PaymentTransaction approve(PaymentTransactionDTO paymentTransactionDTO,
        PaymentRequest paymentRequest) {

        return Optional.of(
                paymentTypeInfoService.getPaymentTypeInfoByUid(paymentTransactionDTO.getUid()))
            .map(paymentTypeInfo -> payInfoService.getPayInfoByPaymentTypeInfoId(
                paymentTypeInfo.getPaymentTypeInfoId()))
            .map(payInfoService::savePayInfo)
            .map(payInfo -> paymentTransactionDTO.toEntity(paymentRequest, payInfo))
            .orElseThrow(() -> new NoSuchElementException("결제에 실패하였습니다."));
    }

}
