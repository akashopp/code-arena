package com.dsa.codearena.controller;

import com.dsa.codearena.config.RabbitMQConfig;
import com.dsa.codearena.dto.SubmissionDto;
import com.dsa.codearena.entity.Submission;
import com.dsa.codearena.service.SubmissionService;
import com.dsa.codearena.util.CommonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/submit-code")
    public ResponseEntity<?> submitCode(@RequestBody SubmissionDto submission) throws Exception {
        Submission submitCode = submissionService.submitCode(submission);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, submitCode.getId());
        return CommonUtils.createErrorResponse("Submitted the code successfully", "success", HttpStatus.CREATED);
    }
}
