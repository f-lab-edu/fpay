package com.flab.fpay.api.pay.service;

import com.flab.fpay.api.enums.ApproveStatus;
import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionResDTO;
import com.flab.fpay.api.pay.repository.PaymentTransactionRepository;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentRequestService paymentRequestService;
    private final ApproveMoney approveMoney;
    private final RedissonClient redissonClient;

    public boolean findPaymentApproveId(BigInteger paymentRequestId) {
        return paymentTransactionRepository
            .findPaymentTransactionByPaymentRequestId(paymentRequestId)
            .isPresent();
    }

    public PaymentTransactionResDTO approvePayment(PaymentTransactionDTO paymentTransactionDTO) {

        if (findPaymentApproveId(paymentTransactionDTO.getPaymentId())) {
            throw new RuntimeException("이미 결제가 완료된 건 입니다.");
        }

        RLock lock = redissonClient.getLock("approveTemp:" + paymentTransactionDTO.getPaymentId());
        boolean available = false;
        try {
            available = lock.tryLock(0, 3, TimeUnit.SECONDS);

            if (!available) {
                throw new RuntimeException("같은 결제건이 현재 결제 진행중 입니다.");
            }

            PaymentRequest paymentRequest = paymentRequestService.getPaymentRequestById(
                paymentTransactionDTO.getPaymentId());
            PaymentTransaction paymentTransaction = null;

            if (!paymentTransactionDTO.getCompanyOrderNumber()
                .equals(paymentRequest.getCompanyOrderNumber()) &&
                !paymentTransactionDTO.getCompanyUserId().equals(paymentRequest.getCompanyUserId())
                &&
                !paymentTransactionDTO.getProductPrice().equals(paymentRequest.getPaymentPrice())) {
                throw new RuntimeException("결제 요청 정보와 승인 정보가 다릅니다.");
            }

            if (paymentRequest.getPaymentType().equals("MONEY")) {
                paymentTransaction = approveMoney.approve(paymentTransactionDTO, paymentRequest);
                paymentTransaction.setPaymentStatus(ApproveStatus.SUCCESS.getValue());
            }

            paymentTransactionRepository.save(paymentTransaction);

            return new PaymentTransactionResDTO(paymentTransaction, paymentRequest);
        } catch (IllegalStateException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (available) {
                lock.unlock();
            }
        }
    }

    public PaymentCancelResponseDTO cancelPayment(PaymentCancelRequestDTO paymentCancelRequestDTO) {
        PaymentCancelResponseDTO paymentCancelResponseDTO = null;

        // 승인된 금액이 취소요청한 금액만큼 존재하는지 체크
        PaymentTransaction paymentTransaction = paymentTransactionRepository
            .findPaymentTransactionByPaymentRequestId(
                paymentCancelRequestDTO.getPaymentId())
            .orElseThrow(() -> new NoSuchElementException("취소요청하신 결제건이 존재하지 않습니다."));

        if (paymentTransaction.getPaymentStatus() == ApproveStatus.CANCEL.getValue()){
            throw new RuntimeException("이미 결제취소 완료된 건 입니다.");
        }

        if (paymentCancelRequestDTO.getProductPrice()
            .compareTo(paymentTransaction.getPaymentPrice()) > 0) {
            throw new RuntimeException("승인된 금액이 취소요청 금액 보다 적습니다.");
        }

        // 결제 취소 진행 후 기존 건 update
        // 만약 승인 금액이 0원이 되면 완전 취소로 가정 or 아닌경우 부분취소
        BigDecimal paymentPrice = paymentTransaction.getPaymentPrice()
            .subtract(paymentCancelRequestDTO.getProductPrice());
        if (paymentPrice.compareTo(BigDecimal.ZERO) == 0) {
            paymentTransaction.setPaymentStatus(ApproveStatus.CANCEL.getValue());
        }
        paymentTransaction.setPaymentPrice(paymentPrice);
        PaymentTransaction saveTransaction = paymentTransactionRepository.save(
            paymentTransaction);

        paymentCancelResponseDTO = new PaymentCancelResponseDTO(saveTransaction,
            paymentCancelRequestDTO);

        log.info(paymentCancelResponseDTO.toString());

        return paymentCancelResponseDTO;
    }


}
