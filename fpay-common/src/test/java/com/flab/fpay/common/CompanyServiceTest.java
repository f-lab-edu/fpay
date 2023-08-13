package com.flab.fpay.common;

import com.flab.fpay.common.company.Company;
import com.flab.fpay.common.company.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.Assert.assertTrue;


@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    @DisplayName("가맹점 추가")
    public void saveCompany(){

        Company company = new Company();

        // 대리점 추가
        company.setCompanyId(BigInteger.valueOf(1));
        company.setCompanyName("무신사");
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());

        Company saveCompany = companyService.saveCompany(company);

        assertTrue(Objects.nonNull(saveCompany));
    }
}
