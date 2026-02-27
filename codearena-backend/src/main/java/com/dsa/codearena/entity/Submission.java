package com.dsa.codearena.entity;

import com.dsa.codearena.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    private String language;
    private Integer problemId;
    @Enumerated(EnumType.STRING)
    private SubmissionStatus submissionStatus = SubmissionStatus.PENDING;
    private String output;
    private Integer userId;
}