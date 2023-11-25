package com.flab.fpay.api.pay;

import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestResDTO;
import com.flab.fpay.api.pay.dto.PaymentResDTO;
import com.flab.fpay.api.pay.service.PaymentApproveService;
import com.flab.fpay.api.pay.service.PaymentCancelService;
import com.flab.fpay.api.pay.service.PaymentRequestService;
import com.flab.fpay.api.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/payment")
class PayController {

    private final PaymentRequestService paymentRequestService;
    private final PaymentService paymentService;
    private final PaymentApproveService paymentApproveService;
    private final PaymentCancelService paymentCancelService;

    @PostMapping("/ready")
    public ResponseEntity<PaymentRequestResDTO> paymentReady(
        @RequestBody PaymentRequestDTO paymentRequestDTO) {

        PaymentRequestResDTO paymentRequestResDTO = paymentRequestService.savePaymentRequest(
            paymentRequestDTO);

        return ResponseEntity.ok(paymentRequestResDTO);
    }

    @PostMapping("/approve")
    public ResponseEntity<PaymentResDTO> paymentApprove(
        @RequestBody PaymentDTO paymentDTO) {

        PaymentResDTO paymentResDTO = paymentApproveService.approvePayment(paymentDTO);

        return ResponseEntity.ok(paymentResDTO);
    }

    @PostMapping("/cancel")
    public ResponseEntity<PaymentCancelResponseDTO> paymentCancel(
        @RequestBody PaymentCancelRequestDTO paymentCancelRequestDTO) {

        PaymentCancelResponseDTO paymentCancelResponseDTO = paymentCancelService.cancelPayment(
            paymentCancelRequestDTO);

        return ResponseEntity.ok(paymentCancelResponseDTO);
    }

}
