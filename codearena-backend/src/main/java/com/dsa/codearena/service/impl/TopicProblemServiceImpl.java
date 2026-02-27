package com.dsa.codearena.service.impl;

import com.dsa.codearena.dto.ProblemDetailsDto;
import com.dsa.codearena.entity.ProblemDetails;
import com.dsa.codearena.repository.ProblemDetailsRepository;
import com.dsa.codearena.service.TopicProblemsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicProblemServiceImpl implements TopicProblemsService {
    @Autowired
    public ProblemDetailsRepository problemDetailsRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ProblemDetailsDto> getProblemsBySlug(String topicSlug) {
        List<ProblemDetails> problemDetailsList = problemDetailsRepository.findByTopicSlug(topicSlug);
        List<ProblemDetailsDto> problemDetailsDto = problemDetailsList.stream().map(
                problemDetails -> mapper.map(problemDetails, ProblemDetailsDto.class)).toList();
        return problemDetailsDto;
    }

    @Override
    public ProblemDetailsDto addProblem(ProblemDetails problemDetails) {
        ProblemDetails saveProblem = problemDetailsRepository.save(problemDetails);
        ProblemDetailsDto problemDetailsDto = mapper.map(saveProblem, ProblemDetailsDto.class);
        return problemDetailsDto;
    }
}
