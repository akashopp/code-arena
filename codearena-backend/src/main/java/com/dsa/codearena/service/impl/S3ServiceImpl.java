package com.dsa.codearena.service.impl;

import com.dsa.codearena.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class S3ServiceImpl implements S3Service {
    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    @Override
    public String getFileContent(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(request);
            return new String(s3Object.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from S3: " + e);
        }
    }

    @Override
    public void uploadFile(String key, String content) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(request, RequestBody.fromString(content, StandardCharsets.UTF_8));
        System.out.println("Uploaded to S3: " + key);
    }
}