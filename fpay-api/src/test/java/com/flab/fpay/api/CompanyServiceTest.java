package com.flab.fpay.api;


import com.flab.fpay.api.company.CompanyRepository;
import com.flab.fpay.api.company.CompanyService;
import com.flab.fpay.common.company.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    @DisplayName("가맹점 추가")
    public void save_company(){

       Company company = new Company();
       company.setCompanyId(BigInteger.valueOf(1));
       company.setCompanyName("무신사");
       company.setCreatedAt(LocalDateTime.now());
       company.setUpdatedAt(LocalDateTime.now());

       given(companyRepository.save(company)).willReturn(company);

//     when
       Company savedCompany = companyService.saveCompany(company);

       assertEquals(BigInteger.valueOf(1), savedCompany.getCompanyId());
       assertEquals("무신사", savedCompany.getCompanyName());

    }

    @Test
    @DisplayName("가맹점 찾기")
    public void find_company(){
       Company company = new Company();
       company.setCompanyId(BigInteger.valueOf(1));
       company.setCompanyName("무신사");
       company.setCreatedAt(LocalDateTime.now());
       company.setUpdatedAt(LocalDateTime.now());

       when(companyRepository.findById(BigInteger.valueOf(1))).thenReturn(Optional.of(company));
       Company findCompany = companyService.getCompanyById(BigInteger.valueOf(1));

       assertThat(company).isEqualTo(findCompany);
    }

}
