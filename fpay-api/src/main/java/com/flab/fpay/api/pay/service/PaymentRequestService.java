package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestResDTO;
import com.flab.fpay.api.pay.repository.PaymentRequestRepository;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentRequestService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final CompanyService companyService;

    public PaymentRequestResDTO savePaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        Company company = companyService.getCompanyById(paymentRequestDTO.getCompanyId());
        PaymentRequest paymentRequest = paymentRequestDTO.toEntity(company);
        PaymentRequest savePayment = paymentRequestRepository.save(paymentRequest);
        return new PaymentRequestResDTO(savePayment.getPaymentRequestId(), "https://test.co.kr" + "/" + savePayment.getPaymentRequestId());
    }

    public PaymentRequest getPaymentRequestById(BigInteger id){
        return paymentRequestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("PaymentRequest not found with id: $id"));
    }

}
