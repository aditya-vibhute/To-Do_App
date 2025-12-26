package com.example.To_do_App.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_do_App.dto.response.DailyReportResponse;
import com.example.To_do_App.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ❌ NO auto-generation here
    // ✅ Fetch today’s report ONLY IF EXISTS
    @GetMapping("/today")
    public DailyReportResponse today(Authentication auth) {

        String email = auth.getName();
        return reportService.getReport(email, LocalDate.now());
    }

    // ✅ Fetch report by date (READ ONLY)
    @GetMapping("/{date}")
    public DailyReportResponse byDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            Authentication auth) {

        String email = auth.getName();
        return reportService.getReport(email, date);
    }

    // ✅ Full history (READ ONLY)
    @GetMapping
    public List<DailyReportResponse> getAllReports(Authentication auth) {

        String email = auth.getName();
        return reportService.getAllReports(email);
    }
}
