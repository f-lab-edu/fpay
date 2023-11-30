package com.flab.fpay.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.fpay.api.pay.dto.PaymentReadyDTO;
import com.flab.fpay.api.pay.dto.PaymentReadyResDTO;
import com.flab.fpay.api.pay.repository.PaymentReadyRepository;
import com.flab.fpay.api.pay.service.PaymentReadyService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentReady;
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

@SpringBootTest(classes = {PaymentReadyService.class})
public class PaymentReadyServiceTest {

    @MockBean
    PaymentReadyService paymentReadyService;

    @Mock
    PaymentReadyRepository paymentReadyRepository;

    PaymentReadyDTO paymentReadyDTO;
    PaymentReady paymentReady;
    Company company;

    @BeforeEach
    void setUp() {

        paymentReadyDTO = PaymentReadyDTO.builder()
            .companyId(BigInteger.valueOf(1))
            .companyOrderNumber("EAEAEAE")
            .companyUserId("baebull70")
            .productName("다이슨 청소기")
            .productCount(1)
            .productPrice(BigDecimal.valueOf(780000))
            .paymentType("MONEY")
            .requestAt(LocalDateTime.now())
            .build();

        company = new Company(BigInteger.valueOf(1), "무신사", LocalDateTime.now(),
            LocalDateTime.now());

        paymentReady = paymentReadyDTO.toEntity(company);

    }

    @Test
    @DisplayName("결제 요청")
    void savePaymentReadyTest() {

//        PaymentReadyService.savePaymentRequest(paymentRequestDTO);

//        when(paymentRequestService.savePaymentRequest(any())).thenReturn(
//            new PaymentRequestResDTO(BigInteger.valueOf(1), "https://test.co.rk")
//        );

//        verify(paymentRequestRepository, times(1)).save(any(PaymentRequest.class));

    }

    @Test
    @DisplayName("결제 요청 정보 찾기")
    void getPaymentRequestByIdTest() {
        when(paymentReadyService.getPaymentReadyById(any())).thenReturn(paymentReady);

        PaymentReady findById = paymentReadyService.getPaymentReadyById(
            BigInteger.valueOf(1));

        assertThat(findById).isNotNull();
        assertThat(findById.getPaymentReadyId()).isEqualTo(paymentReady.getPaymentReadyId());
        assertThat(findById.getCompanyId()).isEqualTo(paymentReady.getCompanyId());

    }

    @Test
    @DisplayName("존재하지 않는 결제 정보를 요청했을 경우")
    void isNotExistsPaymentRequest() {
        // given
        when(paymentReadyRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> {
            PaymentReady findById = paymentReadyService.getPaymentReadyById(
                BigInteger.valueOf(1));
        });
    }

    @Test
    @DisplayName("존재하지 않는 가맹점으로 결제 요청을 했을 경우")
    void isNotExistsCompany(){

    }

}
