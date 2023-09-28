package com.flab.fpay.api.pay.repository;

import com.flab.fpay.common.pay.PaymentTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface PaymentTypeInfoRepository extends JpaRepository<PaymentTypeInfo, BigInteger> {

    Optional<PaymentTypeInfo> findPaymentTypeInfoByUid(BigInteger uid);

}
