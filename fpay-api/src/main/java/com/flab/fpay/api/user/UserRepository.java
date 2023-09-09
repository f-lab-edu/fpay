package com.flab.fpay.api.user;

import com.flab.fpay.common.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, BigInteger> {

    Optional<User> findByUserId(String userId);
}
