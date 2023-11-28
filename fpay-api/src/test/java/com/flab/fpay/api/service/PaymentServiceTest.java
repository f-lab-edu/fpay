package com.flab.fpay.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestResDTO;
import com.flab.fpay.api.pay.repository.PaymentRequestRepository;
import com.flab.fpay.api.pay.service.PaymentRequestService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.pay.PaymentRequest;
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

@SpringBootTest(classes = {PaymentRequestService.class})
public class PaymentServiceTest {

    @MockBean
    PaymentRequestService paymentRequestService;

    @Mock
    PaymentRequestRepository paymentRequestRepository;

    PaymentRequestDTO paymentRequestDTO;
    PaymentRequest paymentRequest;
    Company company;

    @BeforeEach
    void setUp() {
        paymentRequestDTO = PaymentRequestDTO.builder()
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

        paymentRequest = paymentRequestDTO.toEntity(company);
    }


    @Test
    @DisplayName("결제 승인 요청")
    void approvePayment() {
        paymentRequestService.savePaymentRequest(paymentRequestDTO);

//        when(paymentRequestService.savePaymentRequest(any())).thenReturn(
//            new PaymentRequestResDTO(BigInteger.valueOf(1), "https://test.co.rk")
//        );

        verify(paymentRequestRepository, times(1)).save(any(PaymentRequest.class));
    }

    @Test
    @DisplayName("결제 요청 정보가 없는 경우")
    void isNotExistsPaymentRequest() {
        when(paymentRequestService.getPaymentRequestById(any())).thenReturn(paymentRequest);

        PaymentRequest findById = paymentRequestService.getPaymentRequestById(
            BigInteger.valueOf(1));

        assertThat(findById).isNotNull();
        assertThat(findById.getPaymentRequestId()).isEqualTo(paymentRequest.getPaymentRequestId());
        assertThat(findById.getCompanyId()).isEqualTo(paymentRequest.getCompanyId());
    }

    @Test
    @DisplayName("이미 결제가 승인된 건의 경우")
    void isExistsPayment() {
        // given
        when(paymentRequestRepository.findById(any())).thenReturn(Optional.empty());

        // then
        assertThrows(NoSuchElementException.class, () -> {
            PaymentRequest findById = paymentRequestService.getPaymentRequestById(
                BigInteger.valueOf(1));
        });
    }

    @Test
    @DisplayName("결제 요청을 진행한 가맹점 번호가 다른 경우")
    void isDifferentcompanyOrderNumber(){

    }

    @Test
    @DisplayName("결제 취소 요청")
    void cancelPayment(){

    }

    @Test
    @DisplayName("취소 요청한 결제건이 존재하지 않는 경우")
    void isNotExistPayment(){

    }

    @Test
    @DisplayName("결제 취소가 완료된 건인 경우")
    void isExistPaymentCancel(){

    }

    @Test
    @DisplayName("승인된 금액보다 높은 금액을 취소요청하는 경우")
    void lessThanCancellation(){

    }

}
