package com.dsa.codearena.service;

import com.dsa.codearena.dto.SubmissionDto;
import com.dsa.codearena.entity.Submission;

public interface SubmissionService {
    Submission submitCode(SubmissionDto submission) throws Exception;
    Submission getSubmissionById(Integer id) throws Exception;
}
