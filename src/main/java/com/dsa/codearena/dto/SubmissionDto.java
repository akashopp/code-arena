package com.dsa.codearena.dto;

import lombok.Data;

@Data
public class SubmissionDto {
    private String code;
    private String language;
    private Integer problemId;
    private Integer userId;
}