package com.flab.fpay.api.pay;

import com.flab.fpay.common.pay.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, BigInteger> {
}
