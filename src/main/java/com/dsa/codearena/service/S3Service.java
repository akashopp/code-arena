package com.dsa.codearena.service;

public interface S3Service {
    public String getFileContent(String key);
    public void uploadFile(String key, String content);
}
