package com.example.To_do_App.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank(message = "Task title cannot be empty")
    @Size(max = 100, message = "Task title cannot exceed 100 characters")
    private String title;

    // getters & setters
}
