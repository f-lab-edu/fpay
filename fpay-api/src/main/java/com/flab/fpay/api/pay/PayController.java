package com.flab.fpay.api.pay;

import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentResDTO;
import com.flab.fpay.api.pay.mapper.PaymentRestMapper;
import com.flab.fpay.api.pay.request.PaymentApproveRequest;
import com.flab.fpay.api.pay.request.PaymentCancelRequest;
import com.flab.fpay.api.pay.request.PaymentReadyRequest;
import com.flab.fpay.api.pay.response.PaymentApproveResponse;
import com.flab.fpay.api.pay.response.PaymentReadyResponse;
import com.flab.fpay.api.pay.service.PaymentReadyService;
import com.flab.fpay.api.pay.service.PaymentService;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
class PayController {

    private final PaymentReadyService paymentReadyService;
    private final PaymentService paymentService;
    private final PaymentRestMapper paymentRestMapper;

    @PostMapping("/payment/ready")
    public ResponseEntity<PaymentReadyResponse> paymentReady(
        @RequestBody PaymentReadyRequest paymentReadyRequest) {
        PaymentReady PaymentReady = paymentRestMapper.toPaymentReady(paymentReadyRequest);

        PaymentReady = paymentReadyService.savePaymentReady(PaymentReady);

        return ResponseEntity.ok(paymentRestMapper.toPaymentReadyResponse(PaymentReady));
    }

    @PostMapping("/payment/approve")
    public ResponseEntity<PaymentApproveResponse> paymentApprove(
        @RequestBody PaymentApproveRequest paymentApproveRequest) {
        PaymentReady paymentReady = paymentRestMapper.toPaymentReadyFromPaymentApproveRequest(
            paymentApproveRequest);

        PaymentApproveResponse paymentApproveResponse = paymentRestMapper.toPaymentApproveResponse(
            paymentService.approvePayment(paymentReady));

        return ResponseEntity.ok(paymentApproveResponse);
    }

    @PostMapping("/payment/cancel")
    public ResponseEntity<PaymentCancelResponseDTO> paymentCancel(
        @RequestBody PaymentCancelRequest paymentCancelRequest) {

        Payment payment = paymentRestMapper.toPaymentFromPaymentCancelRequest(paymentCancelRequest);
        PaymentCancelResponseDTO paymentCancelResponseDTO = paymentService.cancelPayment(payment);

        return ResponseEntity.ok(paymentCancelResponseDTO);
    }

}
