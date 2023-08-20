package com.flab.fpay.api.pay;

import com.flab.fpay.common.pay.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PayInfoRepository extends JpaRepository<PayInfo, BigInteger> {
}
