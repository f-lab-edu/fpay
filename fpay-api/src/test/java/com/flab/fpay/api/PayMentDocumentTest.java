package com.flab.fpay.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fpay.api.pay.approve.ApproveMoney;
import com.flab.fpay.api.pay.dto.PaymentRequestDTO;
import com.flab.fpay.api.pay.dto.PaymentRequestResDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionDTO;
import com.flab.fpay.api.pay.dto.PaymentTransactionResDTO;
import com.flab.fpay.api.pay.service.PaymentRequestService;
import com.flab.fpay.api.pay.service.PaymentTransactionService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import com.flab.fpay.common.pay.PaymentType;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs // rest docs 자동 설정
public class PayMentDocumentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentRequestService paymentRequestService;

    @MockBean
    private PaymentTransactionService paymentTransactionService;

    @MockBean
    private ApproveMoney approveMoney;

    private PaymentRequestDTO paymentRequestDTO;

    private PaymentTransactionDTO paymentTransactionDTO;


    @BeforeEach
    void setUp() {
//        postRequest = PostRequest.builder()
//                .title("노트북 맥북 프로 16인치 판매합니다.")
//                .content("노트북을 파는 글")
//                .category("디지털/가전")
//                .build();
//
//        member = Member.builder()
//                .email("daangnmarket@admin.com")
//                .password("1q2w3e4r!")
//                .nickname("김당근")
//                .build();
//
//        post = postRequest.toEntity(member);
    }

    @DisplayName("초기 결제 요청")
    @Test
    public void ready_payment() throws Exception {
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

        when(paymentRequestService.savePaymentRequest(any())).thenReturn(
                new PaymentRequestResDTO(BigInteger.valueOf(1), "https://test.co.rk")
        );

        String requestJson = objectMapper.writeValueAsString(paymentRequestDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/payment/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("payment/request-payment",
                        requestFields(
                                fieldWithPath("companyId").description("가맹점ID"),
                                fieldWithPath("companyOrderNumber").description("가맹점 주문번호"),
                                fieldWithPath("companyUserId").description("가맹점 UserId"),
                                fieldWithPath("productName").description("주문 상품명"),
                                fieldWithPath("productCount").description("주문 수량"),
                                fieldWithPath("productPrice").description("상품 금액 ( 총액 )"),
                                fieldWithPath("paymentType").description("결제 타입 ( CRAD/MONEY )"),
                                fieldWithPath("requestAt").description("결제 요청 시각 ( 가맹점 기준 )")
                        ),
                        responseFields(
                                fieldWithPath("paymentId").description("결제 번호"),
                                fieldWithPath("redirectURL").description("결제 페이지(redirect)"),
                                fieldWithPath("createAt").description("주문 생성일")
                        )));

    }

    @DisplayName("결제 승인")
    @Test
    public void approve_payment() throws Exception {

        paymentTransactionDTO = PaymentTransactionDTO.builder()
                .paymentId(BigInteger.valueOf(1))
                .companyId(BigInteger.valueOf(1))
                .companyOrderNumber("EB0010")
                .productPrice(BigDecimal.valueOf(780000))
                .uid(BigInteger.valueOf(1))
                .build();

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentRequestId(BigInteger.valueOf(1));
        paymentRequest.setCompanyId(BigInteger.valueOf(1));
        paymentRequest.setCompanyUserId("baebull70");
        paymentRequest.setProductName("다이슨 청소기");
        paymentRequest.setPaymentPrice(BigDecimal.valueOf(10000));
        paymentRequest.setProductCount(1);
        paymentRequest.setPaymentType("MONEY");

        PaymentTypeInfo paymentTypeInfo = new PaymentTypeInfo();
        paymentTypeInfo.setPaymentTypeInfoId(BigInteger.valueOf(1));
        paymentTypeInfo.setPaymentTypeId(BigInteger.valueOf(1));
        paymentTypeInfo.setUid(BigInteger.valueOf(1));
        paymentTypeInfo.setCreatedAt(LocalDateTime.now());
        paymentTypeInfo.setUpdatedAt(LocalDateTime.now());

        PaymentTransaction paymentTransaction = approveMoney.approve(paymentTransactionDTO, paymentRequest);

        when(paymentTransactionService.approvePayment(any())).thenReturn(new PaymentTransactionResDTO(paymentTransaction, paymentRequest));

        String requestJson = objectMapper.writeValueAsString(paymentTransactionDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/payment/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("payment/approve",
                        requestFields(
                                fieldWithPath("paymentId").description("결제 번호"),
                                fieldWithPath("companyId").description("가맹점ID"),
                                fieldWithPath("companyOrderNumber").description("가맹점 주문번호"),
                                fieldWithPath("companyUserId").description("가맹점 UserId"),
                                fieldWithPath("productPrice").description("상품 금액 ( 총액 )"),
                                fieldWithPath("uid").description("페이 UID")
                        ),
                        responseFields(
                                fieldWithPath("paymentId").description("결제 번호"),
                                fieldWithPath("companyId").description("가맹점ID"),
                                fieldWithPath("companyOrderNumber").description("가맹점 주문번호"),
                                fieldWithPath("companyUserId").description("가맹점 UserId"),
                                fieldWithPath("paymentType").description("결제 타입"),
                                fieldWithPath("productPrice").description("상품 금액 ( 총액 )"),
                                fieldWithPath("productName").description("주문 상품명"),
                                fieldWithPath("productCount").description("주문 수량"),
                                fieldWithPath("createAt").description("생성일"),
                                fieldWithPath("paymentAt").description("승인일")
                        )));
    }

}