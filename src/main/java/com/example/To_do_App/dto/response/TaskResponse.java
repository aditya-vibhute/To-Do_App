package com.example.To_do_App.dto.response;


import java.time.LocalDate;

import lombok.Data;

@Data
public class TaskResponse {

    private Long id;
    private String title;
    private boolean completed;
    private LocalDate createdDate;

    public TaskResponse(Long id, String title,
                        boolean completed, LocalDate createdDate) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdDate = createdDate;
    }

    // getters
}

