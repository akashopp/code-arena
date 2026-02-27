package com.dsa.codearena.service.impl;

import com.dsa.codearena.dto.SubmissionDto;
import com.dsa.codearena.entity.Submission;
import com.dsa.codearena.exception.ResourceNotFoundException;
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
    public Submission submitCode(SubmissionDto submission) throws Exception {
        Submission submitSubmission = mapper.map(submission, Submission.class);
        Submission saveSubmission = submissionRepository.save(submitSubmission);

        if (ObjectUtils.isEmpty(saveSubmission)) {
            throw new RuntimeException("Unable to submit the code");
        }

        return saveSubmission;
    }

    @Override
    public Submission getSubmissionById(Integer id) throws Exception {
        Submission submission = submissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Couldn't find the submission by id : " + id));
        return submission;
    }
}
