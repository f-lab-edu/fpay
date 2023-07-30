package com.flab.fpay.common.domain.pay;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_type")
@Getter
@Setter
@ToString
public class PaymentType implements Serializable {

    @Id
    @Column(name = "payment_type_id")
    private BigInteger paymentTypeId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
