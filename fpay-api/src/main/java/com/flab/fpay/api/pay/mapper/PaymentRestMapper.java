package com.flab.fpay.api.pay.mapper;


import com.flab.fpay.api.pay.request.PaymentApproveRequest;
import com.flab.fpay.api.pay.request.PaymentCancelRequest;
import com.flab.fpay.api.pay.request.PaymentReadyRequest;
import com.flab.fpay.api.pay.response.PaymentApproveResponse;
import com.flab.fpay.api.pay.response.PaymentReadyResponse;
import com.flab.fpay.common.pay.Payment;
import com.flab.fpay.common.pay.PaymentReady;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {

    PaymentReady toPaymentReady(PaymentReadyRequest paymentReadyRequest);
    PaymentReady toPaymentReadyFromPaymentApproveRequest(PaymentApproveRequest paymentApproveRequest);
    PaymentReadyResponse toPaymentReadyResponse(PaymentReady paymentReady);
    PaymentApproveResponse toPaymentApproveResponse(Payment payment);
    Payment toPaymentFromPaymentCancelRequest(PaymentCancelRequest paymentCancelRequest);
}
