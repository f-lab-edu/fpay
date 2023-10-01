package com.flab.fpay.api.pay;

import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestResDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionResDTO;
import com.flab.fpay.api.pay.service.PaymentRequestService;
import com.flab.fpay.api.pay.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/payment")
class PayController {

    private final PaymentRequestService paymentRequestService;
    private final PaymentTransactionService paymentTransactionService;

    @PostMapping("/ready")
    public ResponseEntity<PaymentRequestResDTO> paymentReady(
        @RequestBody PaymentRequestDTO paymentRequestDTO) {

        PaymentRequestResDTO paymentRequestResDTO = paymentRequestService.savePaymentRequest(
            paymentRequestDTO);

        return ResponseEntity.ok(paymentRequestResDTO);
    }

    @PostMapping("/approve")
    public ResponseEntity<PaymentTransactionResDTO> paymentApprove(
        @RequestBody PaymentTransactionDTO paymentTransactionDTO) {

        PaymentTransactionResDTO paymentTransactionResDTO = paymentTransactionService.approvePayment(
            paymentTransactionDTO);

        return ResponseEntity.ok(paymentTransactionResDTO);
    }

    @PostMapping("/cancel")
    public ResponseEntity<PaymentCancelResponseDTO> paymentCancel(
        @RequestBody PaymentCancelRequestDTO paymentCancelRequestDTO) {

        PaymentCancelResponseDTO paymentCancelResponseDTO = paymentTransactionService.cancelPayment(
            paymentCancelRequestDTO);

        return ResponseEntity.ok(paymentCancelResponseDTO);
    }

}
