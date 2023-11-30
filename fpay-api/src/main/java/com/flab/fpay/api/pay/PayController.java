package com.flab.fpay.api.pay;

import com.flab.fpay.api.pay.dto.PaymentCancelRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentCancelResponseDTO;
import com.flab.fpay.api.pay.dto.PaymentDTO;
import com.flab.fpay.api.pay.dto.PaymentResDTO;
import com.flab.fpay.api.pay.mapper.PaymentRestMapper;
import com.flab.fpay.api.pay.request.PaymentReadyCreateRequest;
import com.flab.fpay.api.pay.response.PaymentReadyCreateResponse;
import com.flab.fpay.api.pay.service.PaymentReadyService;
import com.flab.fpay.api.pay.service.PaymentService;
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
    public ResponseEntity<PaymentReadyCreateResponse> paymentReady(
        @RequestBody PaymentReadyCreateRequest paymentReadyCreateRequest) {
        PaymentReady PaymentReady = paymentRestMapper.toPaymentReady(paymentReadyCreateRequest);

        PaymentReady = paymentReadyService.savePaymentReady(PaymentReady);

        return ResponseEntity.ok(paymentRestMapper.toPaymentReadyCreateResponse(PaymentReady));
    }

    @PostMapping("/payment/approve")
    public ResponseEntity<PaymentResDTO> paymentApprove(
        @RequestBody PaymentDTO paymentDTO) {

        PaymentResDTO paymentResDTO = paymentService.approvePayment(paymentDTO);

        return ResponseEntity.ok(paymentResDTO);
    }

    @PostMapping("/payment/cancel")
    public ResponseEntity<PaymentCancelResponseDTO> paymentCancel(
        @RequestBody PaymentCancelRequestDTO paymentCancelRequestDTO) {

        PaymentCancelResponseDTO paymentCancelResponseDTO = paymentService.cancelPayment(
            paymentCancelRequestDTO);

        return ResponseEntity.ok(paymentCancelResponseDTO);
    }

}
