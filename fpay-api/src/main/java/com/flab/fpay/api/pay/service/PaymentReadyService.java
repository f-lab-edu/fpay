package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.api.pay.repository.PaymentReadyRepository;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.PaymentReady;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentReadyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentReadyRepository paymentReadyRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final CompanyService companyService;

    public PaymentReady savePaymentReady(PaymentReady paymentReady) {
         logger.info("[PaymentReady]::" + paymentReady.toString());
//        refact : 존재하는 가맹점인지 validate 필요
        companyService.getCompanyById(paymentReady.getCompanyId());

        PaymentReady savePayment = paymentReadyRepository.save(paymentReady);
        paymentTransactionRepository.save(savePayment.toPaymentTransaction());

        return savePayment;
    }

    public PaymentReady getPaymentReadyById(BigInteger id) {
        return paymentReadyRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("PaymentReady not found with id: $id"));
    }

}
