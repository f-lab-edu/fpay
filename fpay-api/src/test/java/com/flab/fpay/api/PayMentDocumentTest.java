package com.flab.fpay.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fpay.api.pay.PaymentRequestDTO;
import com.flab.fpay.api.pay.PaymentRequestResDTO;
import com.flab.fpay.api.pay.PaymentRequestService;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentType;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
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

    private PaymentRequest paymentRequest;

    private PaymentRequestDTO paymentRequestDTO;

    private PaymentRequestResDTO paymentRequestResDTO;

//    {
//        "companyId": 1,
//            "companyOrderNumber": "EB0010",
//            "companyUserId":"baebull70",
//            "productName": "다이슨 청소기",
//            "productCount": 1,
//            "productPrice": 10000,
//            "paymentType": "MONEY",
//            "requestAt": "2023-09-03T04:32:14.729311"
//    }
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
                PaymentRequestResDTO.builder()
                .paymentId(BigInteger.valueOf(1))
                .redirectURL("https://test.co.kr")
                .createAt(LocalDateTime.now())
                .build());

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

    @DisplayName("결제 요청")
    @Test
    public void approve_payment() throws Exception {

        // find paymentType
        PaymentType paymentType = new PaymentType();
        paymentType.setPaymentTypeId(BigInteger.valueOf(1));
        paymentType.setPaymentType("money");

        // find paymentTypeInfo
        PaymentTypeInfo paymentTypeInfo = new PaymentTypeInfo();
        paymentTypeInfo.setPaymentTypeInfoId(BigInteger.valueOf(1));
        paymentTypeInfo.setPaymentTypeId(paymentType.getPaymentTypeId());
        paymentTypeInfo.setUid(BigInteger.valueOf(1));
        paymentTypeInfo.setCreatedAt(LocalDateTime.now());
        paymentTypeInfo.setUpdatedAt(LocalDateTime.now());

//        PaymentTransaction paymentTransaction = new PaymentTransaction();
//        paymentTransaction.setPaymentTransactionId(BigInteger.valueOf(1));
//        paymentTransaction.setPaymentPrice(BigDecimal.valueOf(28900));
//        paymentTransaction.setUid(paymentTypeInfo.getUid());
//        paymentTransaction.setPaymentTypeInfoId(paymentTypeInfo.getPaymentTypeInfoId());
//        paymentTransaction.setProductId(BigInteger.valueOf(1));
//        paymentType.setPaymentType();

        String requestJson = objectMapper.writeValueAsString(paymentTypeInfo);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/payment/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("payment/approve",
                        requestFields(
                                fieldWithPath("paymentTypeInfoId").description("결제 타입 정보 ID").ignored(),
                                fieldWithPath("paymentTypeId").description("결제 타입 ID"),
                                fieldWithPath("uid").description("고객 ID"),
                                fieldWithPath("createdAt").description("생성일").ignored(),
                                fieldWithPath("updatedAt").description("수정일").ignored()
                        ),
                        responseFields(
                                fieldWithPath("result").description("결제 결과")
                        )));
//                .andDo(document("payment/approve-payment",
//                        Preprocessors.preprocessRequest(prettyPrint()),
//                        Preprocessors.preprocessResponse(prettyPrint())));
    }

}