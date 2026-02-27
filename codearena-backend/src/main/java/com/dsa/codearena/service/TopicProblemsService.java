package com.dsa.codearena.service;

import com.dsa.codearena.dto.ProblemDetailsDto;
import com.dsa.codearena.entity.ProblemDetails;

import java.util.List;

public interface TopicProblemsService {
    List<ProblemDetailsDto> getProblemsBySlug(String topicSlug);
    ProblemDetailsDto addProblem(ProblemDetails problemDetails);
}
