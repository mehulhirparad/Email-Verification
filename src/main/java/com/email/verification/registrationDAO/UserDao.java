package com.email.verification.registrationDAO;

import java.util.List;

import com.email.verification.registerEntity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    List<String> save(User user);
    
    void Update(User user);
    
    User verify(String verificationCode);
}
