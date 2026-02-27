package com.dsa.codearena.service.impl;

import com.dsa.codearena.entity.ProblemDetails;
import com.dsa.codearena.exception.ExistDataException;
import com.dsa.codearena.exception.ResourceNotFoundException;
import com.dsa.codearena.repository.ProblemDetailsRepository;
import com.dsa.codearena.service.ProblemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemDetailsServiceImpl implements ProblemDetailsService {
    @Autowired
    private ProblemDetailsRepository problemDetailsRepository;

    @Override
    public ProblemDetails getProblemBySlug(String slug) throws Exception {
        ProblemDetails problemDetailsList = problemDetailsRepository.findBySlug(slug)
                        .orElseThrow(() -> new ResourceNotFoundException("Problem doesn't exists by its slug"));
        return problemDetailsList;
    }

    @Override
    public ProblemDetails addProblem(ProblemDetails problemDetails) throws Exception {
        Boolean existsBySlug = problemDetailsRepository.existsBySlug(problemDetails.getSlug());
        if(existsBySlug) {
            throw new ExistDataException("Problem already exists");
        }
        ProblemDetails saveProblem = problemDetailsRepository.save(problemDetails);
        return saveProblem;
    }
}
