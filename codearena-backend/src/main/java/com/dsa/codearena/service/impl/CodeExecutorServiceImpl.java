package com.dsa.codearena.service.impl;

import com.dsa.codearena.service.CodeExecutorService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CodeExecutorServiceImpl implements CodeExecutorService {
    private static final String USER_CODE_DIR = "user_submissions";

    @Override
    public String executeCode(String code, String language, String input) throws IOException, InterruptedException {
        String folderName = UUID.randomUUID().toString();
        Path folderPath = Path.of(USER_CODE_DIR, folderName);
        Files.createDirectories(folderPath);
        String fileName = getFileName(language);

        File sourceFile = folderPath.resolve(fileName).toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sourceFile))) {
            writer.write(code);
        }

        File inputFile = folderPath.resolve("input.txt").toFile();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write(input);
        }

        System.out.println(" [x] Files created at: " + folderPath.toAbsolutePath());
        String output = runDockerContainer(folderPath, language);

        FileUtils.deleteDirectory(folderPath.toFile());
        return output;
    }

    private String getFileName(String language) {
        return switch (language.toLowerCase()) {
            case "java" -> "Main.java";
            case "python" -> "script.py";
            case "cpp" -> "main.cpp";
            default -> throw new IllegalArgumentException("Unsupported Language: " + language);
        };
    }

    private String runDockerContainer(Path folderPath, String language) throws IOException, InterruptedException {
        String dockerImage;
        String command;

        switch (language.toLowerCase()) {
            case "java":
                dockerImage = "amazoncorretto:17-alpine-jdk";
                command = "javac Main.java && java Main < input.txt";
                break;
            case "python":
                dockerImage = "python:3.10-alpine";
                command = "python3 script.py < input.txt";
                break;
            case "cpp":
                dockerImage = "gcc:latest";
                command = "g++ -o main main.cpp && ./main < input.txt";
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", folderPath.toAbsolutePath().toString() + ":/app",
                "-w", "/app",
                dockerImage,
                "sh", "-c", command
        );

        Process process = processBuilder.start();

        String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines()
                .collect(Collectors.joining("\n"));

        String errorOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                .lines()
                .collect(Collectors.joining("\n"));

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);

        if (!finished) {
            process.destroy();
            throw new RuntimeException("Time Limit Exceeded");
        }

        if (process.exitValue() != 0) {
            return "ERROR:\n" + errorOutput;
        }

        return output.trim();
    }
}
