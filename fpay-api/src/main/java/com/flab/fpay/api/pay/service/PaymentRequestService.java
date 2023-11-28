package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.api.pay.repository.PaymentRequestRepository;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentRequestService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentRequestRepository paymentRequestRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final CompanyService companyService;

    public PaymentRequest savePaymentRequest(PaymentRequest paymentRequest) {
         logger.info("[paymentRequest]::" + paymentRequest.toString());
//        refact : 존재하는 가맹점인지 validate 필요
        companyService.getCompanyById(paymentRequest.getCompanyId());

        PaymentRequest savePayment = paymentRequestRepository.save(paymentRequest);
        paymentTransactionRepository.save(savePayment.toPaymentTransaction());

        return savePayment;
    }

    public PaymentRequest getPaymentRequestById(BigInteger id) {
        return paymentRequestRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("PaymentRequest not found with id: $id"));
    }

}
