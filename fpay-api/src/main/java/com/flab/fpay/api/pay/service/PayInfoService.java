package com.flab.fpay.api.pay.service;


import com.flab.fpay.api.pay.repository.PayInfoRepository;
import com.flab.fpay.common.pay.PayInfo;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PayInfoService {

    private final PayInfoRepository payInfoRepository;

    public PayInfo getPayInfoByPaymentTypeInfoId(BigInteger paymentTypeInfoId) {
        return payInfoRepository.findPayInfoByPaymentTypeInfoId(paymentTypeInfoId).orElseThrow(
            () -> new NoSuchElementException("PayInfo not found with payInfoTypeId: $id"));
    }

    public PayInfo savePayInfo(PayInfo payInfo) {
        return payInfoRepository.save(payInfo);
    }

}
