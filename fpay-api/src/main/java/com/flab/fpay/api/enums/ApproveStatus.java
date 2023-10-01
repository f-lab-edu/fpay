package com.flab.fpay.api.enums;

public enum ApproveStatus {
    FAIL(-1), SUCCESS(1), CANCEL(2);
    private int status;

    ApproveStatus(int status) {
        this.status = status;
    }

    public int getValue() {
        return status;
    }
}
