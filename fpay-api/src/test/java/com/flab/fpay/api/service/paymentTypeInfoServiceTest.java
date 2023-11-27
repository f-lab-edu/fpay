package com.flab.fpay.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.repository.PaymentTypeInfoRepository;
import com.flab.fpay.api.pay.service.PaymentTypeInfoService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {PaymentTypeInfoService.class})

public class paymentTypeInfoServiceTest {

    @MockBean
    PaymentTypeInfoService paymentTypeInfoService;

    @Mock
    PaymentTypeInfoRepository paymentTypeInfoRepository;

    PaymentTypeInfo paymentTypeInfo;

    @BeforeEach
    void setUp() {
        paymentTypeInfo = new PaymentTypeInfo(BigInteger.valueOf(1), BigInteger.valueOf(1),
            BigInteger.valueOf(1), LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("정상적인 PaymentType 조회")
    void getPaymentTypeInfoByUidTest() {
        when(paymentTypeInfoService.getPaymentTypeInfoByUid(any())).thenReturn(paymentTypeInfo);

        PaymentTypeInfo findById = paymentTypeInfoService.getPaymentTypeInfoByUid(
            BigInteger.valueOf(1));

        assertThat(findById).isNotNull();
        assertThat(findById.getPaymentTypeId()).isEqualTo(paymentTypeInfo.getPaymentTypeId());
        assertThat(findById.getPaymentTypeInfoId()).isEqualTo(paymentTypeInfo.getPaymentTypeInfoId());
        assertThat(findById.getUid()).isEqualTo(paymentTypeInfo.getUid());
    }

    @Test
    @DisplayName("존재하지 않는 PaymentType을 조회하는 경우")
    void isNotExistPaymentType() {
        // given
        when(paymentTypeInfoRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> {
            PaymentTypeInfo findById =  paymentTypeInfoService.getPaymentTypeInfoByUid(
                BigInteger.valueOf(1));
        });
    }
}
