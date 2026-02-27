package com.dsa.codearena.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer id;
    private String message;
    private String email;
    private String token;
}
