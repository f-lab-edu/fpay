package com.flab.fpay.api.pay.mapper;


import com.flab.fpay.api.pay.request.PaymentApproveRequest;
import com.flab.fpay.api.pay.request.PaymentCancelRequest;
import com.flab.fpay.api.pay.request.PaymentReadyRequest;
import com.flab.fpay.api.pay.response.PaymentApproveResponse;
import com.flab.fpay.api.pay.response.PaymentCancelResponse;
import com.flab.fpay.api.pay.response.PaymentReadyResponse;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import java.math.BigDecimal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {

    PaymentReady toPaymentReady(PaymentReadyRequest paymentReadyRequest);
    PaymentReady toPaymentReadyFromPaymentApproveRequest(PaymentApproveRequest paymentApproveRequest);
    Payment toPaymentFromPaymentCancelRequest(PaymentCancelRequest paymentCancelRequest);

    PaymentReadyResponse toPaymentReadyResponse(PaymentReady paymentReady);
    PaymentApproveResponse toPaymentApproveResponse(Payment payment);
    PaymentCancelResponse toPaymentCancelResponse(Payment payment, BigDecimal cancelPrice);
}
