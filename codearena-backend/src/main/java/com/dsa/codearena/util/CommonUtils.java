package com.dsa.codearena.util;

import com.dsa.codearena.handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtils {
    public static ResponseEntity<?> createBuildResponse(String message, HttpStatus httpStatus, Object data) {
        GenericResponse genericResponse = GenericResponse.builder()
                .message(message)
                .httpStatus(httpStatus)
                .data(data)
                .build();
        return genericResponse.create();
    }

    public static ResponseEntity<?> createResponseMessage(String message, HttpStatus httpStatus) {
        GenericResponse genericResponse = GenericResponse.builder()
                .message(message)
                .httpStatus(httpStatus)
                .build();
        return genericResponse.create();
    }

    public static ResponseEntity<?> createErrorResponse(String message, String status, HttpStatus httpStatus) {
        GenericResponse genericResponse = GenericResponse.builder()
                .message(message)
                .status(status)
                .httpStatus(httpStatus)
                .build();
        return genericResponse.create();
    }
}
