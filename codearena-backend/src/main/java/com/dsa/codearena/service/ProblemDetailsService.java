package com.dsa.codearena.service;

import com.dsa.codearena.entity.ProblemDetails;

public interface ProblemDetailsService {
    ProblemDetails getProblemBySlug(String slug) throws Exception;
    ProblemDetails addProblem(ProblemDetails problemDetails) throws Exception;
}
