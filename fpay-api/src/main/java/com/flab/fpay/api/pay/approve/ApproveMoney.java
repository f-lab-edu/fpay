package com.flab.fpay.api.pay.approve;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.service.PayInfoService;
import com.flab.fpay.api.pay.service.PaymentTypeInfoService;
import com.flab.fpay.api.user.UserService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PayInfo;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApproveMoney{

    private final PayInfoService payInfoService;
    private final PaymentTypeInfoService paymentTypeInfoService;

    private Integer SUCCESS = 1;
    private Integer FAILED = -1;

    public PaymentTransaction approve(PaymentTransactionDTO paymentTransactionDTO, PaymentRequest paymentRequest) {
        PaymentTypeInfo paymentTypeInfo = paymentTypeInfoService.getPaymentTypeInfoByUid(paymentTransactionDTO.getUid());
        PayInfo payInfo = payInfoService.getPayInfoByPaymentTypeInfoId(paymentTypeInfo.getPaymentTypeInfoId());
        payInfo.setMoney(payInfo.getMoney().subtract(paymentTransactionDTO.getProductPrice()));
        payInfoService.savePayInfo(payInfo);

        return paymentTransactionDTO.toEntity(paymentRequest, paymentTypeInfo);
    }

}
