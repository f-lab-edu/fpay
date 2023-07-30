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
@Entity(name = "company")
@Getter
@Setter
@ToString
public class Company implements Serializable {

    @Id
    @Column(name = "company_id")
    private BigInteger companyId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
