package com.dsa.codearena.controller;

import com.dsa.codearena.dto.ProblemDetailsDto;
import com.dsa.codearena.entity.ProblemDetails;
import com.dsa.codearena.service.TopicProblemsService;
import com.dsa.codearena.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems/topic")
public class TopicProblemsController {
    @Autowired
    private TopicProblemsService topicProblemsService;

    @GetMapping("/{topicSlug}")
    public ResponseEntity<?> getProblemsByTopicSlug(@PathVariable String topicSlug) {
        List<ProblemDetailsDto> problemsBySlug = topicProblemsService.getProblemsBySlug(topicSlug);
        return CommonUtils.createBuildResponse("Fetched problem list by topic slug", HttpStatus.OK, problemsBySlug);
    }

    @PostMapping("/add-problem")
    public ResponseEntity<?> addProblem(@RequestBody ProblemDetails problemDetails) {
        ProblemDetailsDto addProblem = topicProblemsService.addProblem(problemDetails);
        return CommonUtils.createBuildResponse("Added problem successfully", HttpStatus.CREATED, addProblem);
    }
}
