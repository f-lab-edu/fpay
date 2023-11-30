package com.flab.fpay.api.pay.repository;

import com.flab.fpay.common.pay.PaymentReady;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PaymentReadyRepository extends JpaRepository<PaymentReady, BigInteger> {
}
