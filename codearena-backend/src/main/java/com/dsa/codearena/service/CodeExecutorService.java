package com.dsa.codearena.service;

import java.io.IOException;

public interface CodeExecutorService {
    String executeCode(String code, String language, String input) throws IOException, InterruptedException;
}