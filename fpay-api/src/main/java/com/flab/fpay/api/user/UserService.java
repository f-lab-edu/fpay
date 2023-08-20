package com.flab.fpay.api.user;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

}
