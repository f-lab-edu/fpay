package com.flab.fpay.api.pay.dto;

public class BaseResponseDTO {
    private String message;
    private int status;

    public void setResponseStatus(String message, int status){
        this.message = message;
        this.status = status;
    }
}
