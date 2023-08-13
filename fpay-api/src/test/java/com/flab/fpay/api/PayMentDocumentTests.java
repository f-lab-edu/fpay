package com.flab.fpay.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.company.CompanyRepository;
import com.flab.fpay.common.company.CompanyService;
import com.flab.fpay.common.pay.PaymentRequest;
import com.flab.fpay.common.pay.PaymentTransaction;
import com.flab.fpay.common.pay.PaymentType;
import com.flab.fpay.common.pay.PaymentTypeInfo;
import com.flab.fpay.common.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs // rest docs 자동 설정
public class PayMentDocumentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyService companyService;

    @DisplayName("초기 결제 요청")
    @Test
    public void ready_payment() throws Exception {

        // 대리점 찾기
        Company company = new Company();
        company.setCompanyId(BigInteger.valueOf(1));
        company.setCompanyName("무신사");
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());

        // 결제 요청
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentRequestId(BigInteger.valueOf(1));
        paymentRequest.setCompanyId(company.getCompanyId());
        paymentRequest.setProductId(BigInteger.valueOf(1));
        paymentRequest.setPaymentPrice(BigDecimal.valueOf(28900));
        paymentRequest.setRequestAt(LocalDateTime.now());
        paymentRequest.setCreatedAt(LocalDateTime.now());
        paymentRequest.setUpdatedAt(LocalDateTime.now());

        String requestJson = objectMapper.writeValueAsString(paymentRequest);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/ready")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("payment/request-payment",
                        requestFields(
                                fieldWithPath("companyId").description("가맹점ID"),
                                fieldWithPath("paymentRequestId").description("결제요청ID"),
                                fieldWithPath("productId").description("상품ID"),
                                fieldWithPath("paymentPrice").description("상품가격"),
                                fieldWithPath("requestAt").description("요청일").ignored(),
                                fieldWithPath("createdAt").description("생성일").ignored(),
                                fieldWithPath("updatedAt").description("수정일").ignored()
                        ),
                        responseFields(
                                fieldWithPath("redirectUrl").description("결제 페이지(redirect)")
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

        this.mockMvc.perform(MockMvcRequestBuilders.post("/payment/approve")
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