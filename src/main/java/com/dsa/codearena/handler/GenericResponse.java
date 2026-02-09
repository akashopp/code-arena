package com.dsa.codearena.handler;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

@Builder
public class GenericResponse {
    private HttpStatus httpStatus;
    private String message;
    private Object data;

    public ResponseEntity<?> create() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", message);
        if(!ObjectUtils.isEmpty(data)) {
            map.put("data", data);
        }

        return new ResponseEntity<>(map, httpStatus);
    }
}