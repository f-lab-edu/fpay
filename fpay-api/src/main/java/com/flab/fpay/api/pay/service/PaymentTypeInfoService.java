package com.flab.fpay.api.pay.service;


import com.flab.fpay.api.pay.repository.PaymentTypeInfoRepository;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentTypeInfoService {

    private final PaymentTypeInfoRepository paymentTypeInfoRepository;

    public PaymentTypeInfo getPaymentTypeInfoByUid(BigInteger uid) {
        return paymentTypeInfoRepository.findPaymentTypeInfoByUid(uid).orElseThrow(
            () -> new NoSuchElementException("PaymentTypeInfo not found with uid: $id"));
    }

}
