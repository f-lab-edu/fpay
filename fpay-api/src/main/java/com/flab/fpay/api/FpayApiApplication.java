package com.flab.fpay.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = {"com.flab.fpay.common"})
public class FpayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FpayApiApplication.class,args);
    }
}
