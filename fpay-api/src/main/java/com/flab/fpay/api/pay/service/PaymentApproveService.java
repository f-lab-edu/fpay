package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.enums.ApproveStatus;
import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentDTO;
import com.flab.fpay.api.pay.dto.PaymentResDTO;
import com.flab.fpay.api.pay.repository.PaymentRepository;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentRequest;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentApproveService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final PaymentRequestService paymentRequestService;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final RedissonClient redissonClient;
    private final ApproveMoney approveMoney;

    //   결제 승인
    public PaymentResDTO approvePayment(PaymentDTO paymentDTO) {

        if (paymentService.findPaymentApproveId(paymentDTO.getPaymentId())) {
            throw new RuntimeException("이미 결제가 완료된 건 입니다.");
        }

        RLock lock = redissonClient.getLock("approveTemp:" + paymentDTO.getPaymentId());
        boolean available = false;

        try {
            available = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("같은 결제건이 현재 결제 진행중 입니다.");
            }

            PaymentRequest paymentRequest = paymentRequestService.getPaymentRequestById(
                paymentDTO.getPaymentId());
            Payment payment = null;

            if (!paymentDTO.getCompanyOrderNumber()
                .equals(paymentRequest.getCompanyOrderNumber()) &&
                !paymentDTO.getCompanyUserId().equals(paymentRequest.getCompanyUserId())
                &&
                !paymentDTO.getProductPrice().equals(paymentRequest.getPaymentPrice())) {
                throw new RuntimeException("결제 요청 정보와 승인 정보가 다릅니다.");
            }

            if (paymentRequest.getPaymentType().equals("MONEY")) {
                payment = approveMoney.approve(paymentDTO, paymentRequest);
                payment.setPaymentStatus(ApproveStatus.SUCCESS.getValue());
            }

            log.info("[PaymentApprove]:::" + payment.toString());

            Payment savePayment = paymentRepository.save(payment);
            paymentTransactionRepository.save(savePayment.toPaymentTransaction());

            return new PaymentResDTO(savePayment, paymentRequest);

        } catch (IllegalStateException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (available) {
                lock.unlock();
            }
        }
    }
}
