package com.flab.fpay.api.pay.repository;

import com.flab.fpay.common.pay.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface PayInfoRepository extends JpaRepository<PayInfo, BigInteger> {

    Optional<PayInfo> findPayInfoByPaymentTypeInfoId(BigInteger paymentTypeInfoId);

}
