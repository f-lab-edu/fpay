package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.pay.repository.PaymentRepository;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentRepository paymentRepository;

    public boolean findPaymentApproveId(BigInteger paymentRequestId) {
        return paymentRepository
            .findPaymentByPaymentRequestId(paymentRequestId)
            .isPresent();
    }

}
