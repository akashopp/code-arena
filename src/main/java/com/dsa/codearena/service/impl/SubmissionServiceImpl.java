package com.dsa.codearena.service.impl;

import com.dsa.codearena.dto.SubmissionDto;
import com.dsa.codearena.entity.Submission;
import com.dsa.codearena.repository.SubmissionRepository;
import com.dsa.codearena.service.SubmissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean submitCode(SubmissionDto submission) {
        Submission submitSubmission = mapper.map(submission, Submission.class);
        Submission saveSubmission = submissionRepository.save(submitSubmission);
        return !ObjectUtils.isEmpty(saveSubmission);
    }
}
