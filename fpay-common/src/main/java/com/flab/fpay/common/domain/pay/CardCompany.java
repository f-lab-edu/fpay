package com.flab.fpay.common.domain.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Table
@Entity(name = "card_company")
@Getter
@Setter
@ToString
public class CardCompany {

    @Id
    @Column(name = "card_company_id")
    private BigInteger cardCompanyId;

    @Column(name = "card_company_name")
    private String cardCompanyName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
