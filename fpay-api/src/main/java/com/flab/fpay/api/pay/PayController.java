package com.flab.fpay.api.pay;

import com.flab.fpay.common.pay.PaymentTypeInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/payment")
class PayController {

    private final PaymentRequestService paymentRequestService;

    @PostMapping("/ready")
    public ResponseEntity<PaymentRequestResDTO> paymentReady(@RequestBody PaymentRequestDTO paymentRequestDTO){

        PaymentRequestResDTO paymentRequestResDTO = paymentRequestService.savePaymentRequest(paymentRequestDTO);

        return ResponseEntity.ok(paymentRequestResDTO);
    }

    @PostMapping("/approve")
    public ResponseEntity<Map> paymentApprove(PaymentTypeInfo paymentTypeInfo){
        return ResponseEntity.ok(Map.of("result","success"));
    }

}
