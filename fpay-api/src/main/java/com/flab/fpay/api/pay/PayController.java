package com.flab.fpay.api.pay;

import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentType;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import com.flab.fpay.common.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PayController {

    @PostMapping("/ready")
    public ResponseEntity<Map> paymentReady(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(Map.of("redirectUrl","http://test.co.kr"));
    }

    @PostMapping("/approve")
    public ResponseEntity<Map> paymentApprove(PaymentTypeInfo paymentTypeInfo){
        return ResponseEntity.ok(Map.of("result","success"));
    }


}
