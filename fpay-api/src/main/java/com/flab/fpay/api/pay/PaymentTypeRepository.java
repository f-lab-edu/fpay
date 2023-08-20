package com.flab.fpay.api.pay;

import com.flab.fpay.common.pay.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, BigInteger> {
}
