package com.dsa.codearena.service.impl;

import com.dsa.codearena.entity.LoginRequest;
import com.dsa.codearena.entity.LoginResponse;
import com.dsa.codearena.entity.User;
import com.dsa.codearena.exception.ExistDataException;
import com.dsa.codearena.exception.UserNotFoundException;
import com.dsa.codearena.repository.UserRepository;
import com.dsa.codearena.service.JwtService;
import com.dsa.codearena.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public Boolean register(User user) throws Exception {
        Boolean findUserByEmail = userRepository.existsByEmail(user.getEmail());
        if(findUserByEmail) {
            throw new ExistDataException("User already exists");
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return LoginResponse.builder()
                    .message("User Password doesn't match")
                    .build();
        }

        String token = jwtService.generateToken(user.getEmail());

        return LoginResponse.builder()
                .id(user.getId())
                .message("Login successful")
                .token(token)
                .email(loginRequest.getEmail())
                .build();
    }
}
