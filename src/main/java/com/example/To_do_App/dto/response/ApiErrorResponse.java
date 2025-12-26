package com.example.To_do_App.dto.response;


import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> errors;

    public ApiErrorResponse(int status, String error, Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.errors = errors;
    }

    // getters
}

