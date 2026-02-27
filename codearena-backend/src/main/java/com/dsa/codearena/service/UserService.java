package com.dsa.codearena.service;

import com.dsa.codearena.entity.LoginRequest;
import com.dsa.codearena.entity.LoginResponse;
import com.dsa.codearena.entity.User;

public interface UserService {
    Boolean register(User user) throws Exception;
    LoginResponse login(LoginRequest loginRequest) throws Exception;
}
