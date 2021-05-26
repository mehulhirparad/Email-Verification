package com.email.verification.regisService;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.email.verification.registerEntity.RegisterUser;
import com.email.verification.registerEntity.User;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);
    
    void save(RegisterUser registerUser);
    
    boolean verify(String verificationCode);
}
