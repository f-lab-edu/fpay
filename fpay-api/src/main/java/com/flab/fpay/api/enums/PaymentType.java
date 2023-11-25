package com.flab.fpay.api.enums;

public enum PaymentType {
    MONEY("MONEY"), CARD("CARD");

    private String value;

    PaymentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
