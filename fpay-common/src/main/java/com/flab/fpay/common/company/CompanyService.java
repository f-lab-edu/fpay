package com.flab.fpay.common.company;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {

//    @Autowired
//    CompanyRepository companyRepository;

    private CompanyRepository companyRepository;

    public Company saveCompany(Company entity){
        return companyRepository.save(entity);
    }

    public Company getCompanyById(BigInteger id){
        return companyRepository.findById(id.longValue()).orElseThrow(() -> new NoSuchElementException("Company not found with id: $id"));
    }

}
