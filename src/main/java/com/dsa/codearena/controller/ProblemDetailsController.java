package com.dsa.codearena.controller;

import com.dsa.codearena.entity.ProblemDetails;
import com.dsa.codearena.service.ProblemDetailsService;
import com.dsa.codearena.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemDetailsController {
    @Autowired
    private ProblemDetailsService problemDetailsService;

    @GetMapping("/{problemSlug}")
    public ResponseEntity<?> getProblemByProblemSlug(@PathVariable String problemSlug) throws Exception {
        ProblemDetails problemBySlug = problemDetailsService.getProblemBySlug(problemSlug);
        return CommonUtils.createBuildResponse("Fetched problem by its slug successfully", HttpStatus.OK, problemBySlug);
    }

    @PostMapping("/add-problem")
    public ResponseEntity<?> addProblem(@RequestBody ProblemDetails problemDetails) throws Exception {
        ProblemDetails addProblem = problemDetailsService.addProblem(problemDetails);
        return CommonUtils.createBuildResponse("Added Problem Successfully", HttpStatus.CREATED, addProblem);
    }
}
