package com.flab.fpay.api.user;


import com.flab.fpay.common.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(BigInteger id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: $id"));
    }

    public User getUserByUserId (String userId){
        return userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchElementException("User not found with user_id: $userId"));
    }

}
