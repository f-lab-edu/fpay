package com.flab.fpay.api.pay;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentRequestService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final CompanyService companyService;

    public PaymentRequestResDTO savePaymentRequest(PaymentRequestDTO paymentRequestDTO){

        Company company = companyService.getCompanyById(paymentRequestDTO.getCompanyId());

        PaymentRequest paymentRequest = paymentRequestDTO.toEntity(company);

        PaymentRequest savePayment = paymentRequestRepository.save(paymentRequest);

        return PaymentRequestResDTO.builder()
                .paymentId(savePayment.getPaymentRequestId())
                .redirectURL("https://test.co.kr" + "/" + savePayment.getPaymentRequestId())
                .createAt(LocalDateTime.now())
                .build();

    }

}
