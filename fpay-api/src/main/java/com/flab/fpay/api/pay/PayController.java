package com.flab.fpay.api.pay;

import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PayController {

    @Autowired
    CompanyService companyService;

    @PostMapping("/ready")
    public ResponseEntity<Map> paymentReady(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(Map.of("redirectUrl","http://test.co.kr"));
    }

    @PostMapping("/approve")
    public ResponseEntity<Map> paymentApprove(PaymentTypeInfo paymentTypeInfo){
        return ResponseEntity.ok(Map.of("result","success"));
    }

}
