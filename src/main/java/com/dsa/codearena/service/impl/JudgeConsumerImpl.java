package com.dsa.codearena.service.impl;

import com.dsa.codearena.config.RabbitMQConfig;
import com.dsa.codearena.entity.Submission;
import com.dsa.codearena.enums.SubmissionStatus;
import com.dsa.codearena.exception.ResourceNotFoundException;
import com.dsa.codearena.repository.SubmissionRepository;
import com.dsa.codearena.service.JudgeConsumer;
import com.dsa.codearena.service.SubmissionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeConsumerImpl implements JudgeConsumer {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    @Override
    public void consumeMessage(Integer submissionId) throws Exception {
        try {
            System.out.println(" [x] Received Submission ID: " + submissionId);
            Submission submission = submissionService.getSubmissionById(submissionId);
            processSubmission(submission);
        } catch (ResourceNotFoundException e) {
            System.err.println(" [!] Invalid Submission ID received: " + submissionId + ". Dropping message.");
        } catch (Exception e) {
            System.err.println(" [!] Error processing submission: " + e.getMessage());
        }
    }

    @Override
    public void processSubmission(Submission submission) {
        System.out.println("Processing Code for Language: " + submission.getLanguage());
        System.out.println("Code Content: \n" + submission.getCode());

        submission.setSubmissionStatus(SubmissionStatus.ACCEPTED);
        submissionRepository.save(submission);
    }
}
