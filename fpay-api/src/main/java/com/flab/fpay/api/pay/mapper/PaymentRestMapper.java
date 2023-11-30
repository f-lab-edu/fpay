package com.flab.fpay.api.pay.mapper;


import com.flab.fpay.api.pay.request.PaymentReadyCreateRequest;
import com.flab.fpay.api.pay.response.PaymentReadyCreateResponse;
import com.flab.fpay.common.pay.PaymentReady;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {

    PaymentReady toPaymentReady(PaymentReadyCreateRequest paymentReadyCreateRequest);
    PaymentReadyCreateResponse toPaymentReadyCreateResponse(PaymentReady paymentReady);

}
