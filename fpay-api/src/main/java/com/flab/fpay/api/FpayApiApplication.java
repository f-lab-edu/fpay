package com.flab.fpay.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.flab.fpay.common"})
public class FpayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpayApiApplication.class,args);
    }
}
