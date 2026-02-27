package com.dsa.codearena.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetailsDto {
    private String id;
    private String title;
    private String difficulty;
    private String slug;
    private String topicSlug;
    private Integer topicId;
}