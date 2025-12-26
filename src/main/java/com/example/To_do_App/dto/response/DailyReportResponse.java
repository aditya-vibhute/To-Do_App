package com.example.To_do_App.dto.response;


import java.time.LocalDate;

import lombok.Data;

@Data
public class DailyReportResponse {

    private LocalDate date;
    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private double completionPercentage;
    private String summaryText;

    public DailyReportResponse(LocalDate date,
            long totalTasks,
            long completedTasks,
            long pendingTasks,
            double completionPercentage,
            String summaryText) {

        this.date = date;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.completionPercentage = completionPercentage;
        this.summaryText = summaryText;
    }

    // getters
}

