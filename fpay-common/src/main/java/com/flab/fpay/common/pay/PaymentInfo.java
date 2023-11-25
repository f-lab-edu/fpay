package com.flab.fpay.common.pay;

import com.flab.fpay.common.card.CardInfo;

public class PaymentInfo {

    public PayInfo toPayInfoEntity(){
        return new PayInfo();
    }

    public CardInfo toCardInfoEntity(){
        return new CardInfo();
    }

}
