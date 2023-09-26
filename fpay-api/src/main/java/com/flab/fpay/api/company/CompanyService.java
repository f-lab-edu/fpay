package com.flab.fpay.api.company;

import com.flab.fpay.common.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Company saveCompany(Company entity) {
        return companyRepository.save(entity);
    }

    public Company getCompanyById(BigInteger id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Company not found with id: $id"));
    }

}
