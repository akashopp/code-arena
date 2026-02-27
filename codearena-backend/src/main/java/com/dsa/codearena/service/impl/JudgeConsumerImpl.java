package com.dsa.codearena.service.impl;

import com.dsa.codearena.config.RabbitMQConfig;
import com.dsa.codearena.entity.Submission;
import com.dsa.codearena.entity.TestCase;
import com.dsa.codearena.enums.SubmissionStatus;
import com.dsa.codearena.exception.ResourceNotFoundException;
import com.dsa.codearena.repository.SubmissionRepository;
import com.dsa.codearena.repository.TestCaseRepository;
import com.dsa.codearena.service.CodeExecutorService;
import com.dsa.codearena.service.JudgeConsumer;
import com.dsa.codearena.service.S3Service;
import com.dsa.codearena.service.SubmissionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Service
public class JudgeConsumerImpl implements JudgeConsumer {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private CodeExecutorService codeExecutorService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private S3Service s3Service;

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
    public void processSubmission(Submission submission) throws IOException, InterruptedException {
        System.out.println("Processing Code for Language: " + submission.getLanguage());
        System.out.println("Code Content: \n" + submission.getCode());
        try {
            List<TestCase> testcaseList = testCaseRepository.findByProblemId(submission.getProblemId());
            if(CollectionUtils.isEmpty(testcaseList)) {
                System.err.println("No test cases found for problem ID: " + submission.getProblemId());
                submission.setSubmissionStatus(SubmissionStatus.INTERNAL_ERROR);
                submissionRepository.save(submission);
                return;
            }

            boolean allPassed = true;
            for(TestCase testCase: testcaseList) {
                try {
                    String inputContent = s3Service.getFileContent(testCase.getInputKey());
                    String expectedOutputContent = s3Service.getFileContent(testCase.getOutputKey());

                    String actualOutput = codeExecutorService.executeCode(
                            submission.getCode(),
                            submission.getLanguage(),
                            inputContent
                    );

                    if (actualOutput.startsWith("ERROR:")) {
                        submission.setSubmissionStatus(SubmissionStatus.COMPILE_ERROR);
                        submission.setOutput(actualOutput);
                        allPassed = false;
                        break;
                    }

                    if (!actualOutput.trim().equals(expectedOutputContent.trim())) {
                        submission.setSubmissionStatus(SubmissionStatus.WRONG_ANSWER);
                        submission.setOutput("Expected: " + expectedOutputContent + "\nActual: " + actualOutput);
                        allPassed = false;
                        break;
                    }
                } catch (Exception e) {
                    submission.setSubmissionStatus(SubmissionStatus.RUNTIME_ERROR);
                    submission.setOutput(e.getMessage());
                    allPassed = false;
                    break;
                }
            }

            if(allPassed) {
                submission.setSubmissionStatus(SubmissionStatus.ACCEPTED);
                submission.setOutput("All " + testcaseList.size() + " test cases passed!");
            }

            submissionRepository.save(submission);
        } catch (Exception e) {
            System.err.println("Error executing code: " + e.getMessage());
            submission.setSubmissionStatus(SubmissionStatus.INTERNAL_ERROR);
            submission.setOutput(e.getMessage());
        }

        submissionRepository.save(submission);
    }
}
