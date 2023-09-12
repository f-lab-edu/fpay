package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionResDTO;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRequestService paymentRequestService;
    private final ApproveMoney approveMoney;
    private final RedissonClient redissonClient;

    public PaymentTransactionResDTO approvePayment(PaymentTransactionDTO paymentTransactionDTO){
        RLock lock = redissonClient.getLock(paymentTransactionDTO.getPaymentId().toString());

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("lock 획득 실패");
            }

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

            return new PaymentTransactionResDTO(paymentTransaction, paymentRequest);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

}
