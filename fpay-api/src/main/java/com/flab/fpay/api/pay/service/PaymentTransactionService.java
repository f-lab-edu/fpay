package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionResDTO;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRequestService paymentRequestService;
    private final ApproveMoney approveMoney;

    public PaymentTransactionResDTO approvePayment(PaymentTransactionDTO paymentTransactionDTO){

        PaymentRequest paymentRequest = paymentRequestService.getPaymentRequestById(paymentTransactionDTO.getPaymentId()); //      결제 요청 ( ready step ) 단계에서 저장된 데이터 불러오기
        PaymentTransaction paymentTransaction = null;

        if (!paymentTransactionDTO.getCompanyOrderNumber().equals(paymentRequest.getCompanyOrderNumber()) &&
         !paymentTransactionDTO.getCompanyUserId().equals(paymentRequest.getCompanyUserId()) &&
         !paymentTransactionDTO.getProductPrice().equals(paymentRequest.getPaymentPrice())){
            throw new RuntimeException("결제 요청 정보와 승인 정보가 다릅니다.");
        }

        if (paymentRequest.getPaymentType().equals("MONEY")){
            paymentTransaction = approveMoney.approve(paymentTransactionDTO, paymentRequest);
        }

        paymentTransactionRepository.save(paymentTransaction);

        return PaymentTransactionResDTO.builder()
                .paymentId(paymentTransaction.getPaymentRequestId())
                .companyId(paymentTransaction.getPaymentCompany())
                .companyOrderNumber(paymentTransaction.getCompanyOrderNumber())
                .companyUserId(paymentTransaction.getCompanyUserId())
                .paymentType(paymentRequest.getPaymentType())
                .productPrice(paymentTransaction.getPaymentPrice())
                .productName(paymentRequest.getProductName())
                .productCount(paymentRequest.getProductCount())
                .createAt(paymentTransaction.getCreatedAt())
                .paymentAt(paymentTransaction.getPaymentAt())
                .build();
    }



}
