package com.flab.fpay.api.company;


import com.flab.fpay.common.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CompanyRepository extends JpaRepository<Company, BigInteger> {

//    @Override
//    Optional<Company> findById(BigInteger id);
//
//    @Override
//    Company save(Company entity);

}
