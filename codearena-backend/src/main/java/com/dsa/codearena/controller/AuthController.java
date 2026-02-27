package com.dsa.codearena.controller;

import com.dsa.codearena.entity.LoginRequest;
import com.dsa.codearena.entity.LoginResponse;
import com.dsa.codearena.entity.User;
import com.dsa.codearena.service.UserService;
import com.dsa.codearena.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
        Boolean saveUser = userService.register(user);
        if(saveUser) {
            return CommonUtils.createBuildResponse("User saved successfully", HttpStatus.CREATED, "");
        }
        return CommonUtils.createBuildResponse("Error registering user", HttpStatus.INTERNAL_SERVER_ERROR, "");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = userService.login(loginRequest);
        return CommonUtils.createBuildResponse("User logged in successfully", HttpStatus.OK, loginResponse);
    }
}