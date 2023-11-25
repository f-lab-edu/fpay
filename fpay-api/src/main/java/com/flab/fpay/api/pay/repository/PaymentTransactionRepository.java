package com.flab.fpay.api.pay.repository;

import com.flab.fpay.common.pay.PaymentTransaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, BigInteger> {

}
