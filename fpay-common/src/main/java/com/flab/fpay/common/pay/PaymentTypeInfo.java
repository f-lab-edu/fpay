package com.flab.fpay.common.pay;

import com.flab.fpay.common.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "payment_type_info")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeInfo implements Serializable {

    @Id
    @Column(name = "payment_type_info_id")
    private BigInteger paymentTypeInfoId;

    @Column(name = "uid")
    private BigInteger uid;

    @Column(name = "payment_type_id")
    private BigInteger paymentTypeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
