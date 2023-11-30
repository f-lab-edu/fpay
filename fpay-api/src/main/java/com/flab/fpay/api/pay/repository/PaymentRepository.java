package com.flab.fpay.api.pay.repository;

import com.flab.fpay.common.pay.Payment;
import java.math.BigInteger;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, BigInteger> {

    Optional<Payment> findPaymentByPaymentReadyId(BigInteger paymentReadyId);
}
