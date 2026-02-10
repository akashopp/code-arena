package com.dsa.codearena.service;

import com.dsa.codearena.entity.Submission;

public interface JudgeConsumer {
    void consumeMessage(Integer submissionId) throws Exception;
    void processSubmission(Submission submission);
}
