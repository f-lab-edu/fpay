package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.enums.ApproveStatus;
import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentDTO;
import com.flab.fpay.api.pay.dto.PaymentResDTO;
import com.flab.fpay.api.pay.repository.PaymentRepository;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRepository paymentRepository;
    private final RedissonClient redissonClient;
    private final PaymentReadyService paymentReadyService;
    private final ApproveMoney approveMoney;

    public boolean findPaymentApproveId(BigInteger paymentReadyId) {
        return paymentRepository
            .findPaymentByPaymentReadyId(paymentReadyId)
            .isPresent();
    }

    //   결제 승인
    public PaymentResDTO approvePayment(PaymentDTO paymentDTO) {

        if (findPaymentApproveId(paymentDTO.getPaymentId())) {
            throw new RuntimeException("이미 결제가 완료된 건 입니다.");
        }

        RLock lock = redissonClient.getLock("approveTemp:" + paymentDTO.getPaymentId());
        boolean available = false;

        try {
            available = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("같은 결제건이 현재 결제 진행중 입니다.");
            }

            PaymentReady paymentReady = paymentReadyService.getPaymentReadyById(
                paymentDTO.getPaymentId());
            Payment payment = null;

            if (!paymentDTO.getCompanyOrderNumber()
                .equals(paymentReady.getCompanyOrderNumber()) &&
                !paymentDTO.getCompanyUserId().equals(paymentReady.getCompanyUserId())
                &&
                !paymentDTO.getProductPrice().equals(paymentReady.getPaymentPrice())) {
                throw new RuntimeException("결제 요청 정보와 승인 정보가 다릅니다.");
            }

            if (paymentReady.getPaymentType().equals("MONEY")) {
                payment = approveMoney.approve(paymentDTO, paymentReady);
                payment.setPaymentStatus(ApproveStatus.SUCCESS.getValue());
            }

            logger.info("[PaymentApprove]:::" + payment.toString());

            Payment savePayment = paymentRepository.save(payment);
            paymentTransactionRepository.save(savePayment.toPaymentTransaction());

            return new PaymentResDTO(savePayment, paymentReady);

        } catch (IllegalStateException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (available) {
                lock.unlock();
            }
        }
    }

    public PaymentCancelResponseDTO cancelPayment(PaymentCancelRequestDTO paymentCancelRequestDTO) {
        logger.info("[PaymentCancel]:::" + paymentCancelRequestDTO.toString());

        PaymentCancelResponseDTO paymentCancelResponseDTO = null;

        // 승인된 금액이 취소요청한 금액만큼 존재하는지 체크
        Payment payment = paymentRepository
            .findById(
                paymentCancelRequestDTO.getPaymentId())
            .orElseThrow(() -> new NoSuchElementException("취소요청하신 결제건이 존재하지 않습니다."));

        if (payment.getPaymentStatus() == ApproveStatus.CANCEL.getValue()) {
            throw new RuntimeException("이미 결제취소 완료된 건 입니다.");
        }

        if (payment.getPaymentPrice()
            .compareTo(paymentCancelRequestDTO.getProductPrice()) < 0) {
            throw new RuntimeException("승인된 금액이 취소요청 금액 보다 적습니다.");
        }

        RLock lock = redissonClient.getLock("cancelTemp:" + paymentCancelRequestDTO.getPaymentId());
        boolean available = false;

        try {
            available = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("같은 결제건이 현재 결제 취소 진행중 입니다.");
            }

            BigDecimal paymentPrice = payment.getPaymentPrice()
                .subtract(paymentCancelRequestDTO.getProductPrice().add(
                    paymentCancelRequestDTO.getProductPrice().multiply(BigDecimal.valueOf(0.10)))
                );

            if (paymentPrice.compareTo(BigDecimal.ZERO) == 0) {
                payment.setPaymentStatus(ApproveStatus.CANCEL.getValue());
            }

            payment.setPaymentPrice(paymentPrice);
            Payment savePayment = paymentRepository.save(payment);
            paymentTransactionRepository.save(savePayment.toPaymentTransaction());

            paymentCancelResponseDTO = new PaymentCancelResponseDTO(savePayment,
                paymentCancelRequestDTO);

            return paymentCancelResponseDTO;
        } catch (IllegalStateException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (available) {
                lock.unlock();
            }
        }
    }
}
