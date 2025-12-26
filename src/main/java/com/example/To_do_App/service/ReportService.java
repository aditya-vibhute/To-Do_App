package com.example.To_do_App.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.To_do_App.dto.response.DailyReportResponse;
import com.example.To_do_App.entity.DailyReport;
import com.example.To_do_App.entity.User;
import com.example.To_do_App.repository.DailyReportRepository;
import com.example.To_do_App.repository.TaskRepository;
import com.example.To_do_App.repository.UserRepository;

@Service
public class ReportService {

    private final DailyReportRepository reportRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ReportService(DailyReportRepository reportRepository,
                         TaskRepository taskRepository,
                         UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /* =========================
       READ APIs (NO GENERATION)
       ========================= */

    public DailyReportResponse getReport(String email, LocalDate date) {

        User user = getUser(email);

        DailyReport report = reportRepository
            .findByUserAndDate(user, date)
            .orElseThrow(() ->
                new RuntimeException("Report not found for date: " + date));

        return toResponse(report);
    }

    public List<DailyReportResponse> getAllReports(String email) {

        User user = getUser(email);

        return reportRepository.findByUser(user)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    /* =========================
       GENERATION (EXPLICIT ONLY)
       ========================= */

    public void generateLastActiveDayReport(String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        taskRepository.findLastActiveDate(user, today)
            .filter(date -> !reportRepository.existsByUserAndDate(user, date))
            .ifPresent(date -> generateAndSave(user, date));
    }


    private DailyReport generateAndSave(User user, LocalDate date) {

        long total = taskRepository.countByUserAndCreatedDate(user, date);
        long completed =
            taskRepository.countByUserAndCreatedDateAndCompleted(
                user, date, true);

        DailyReport report = new DailyReport();
        report.setUser(user);
        report.setDate(date);
        report.setTotalTasks(total);
        report.setCompletedTasks(completed);
        report.setPendingTasks(total - completed);
        report.setCompletionPercentage(
            total == 0 ? 0 : (completed * 100.0) / total);
        report.setSummaryText("Auto-generated daily report");

        return reportRepository.save(report);
    }

    /* =========================
       HELPERS
       ========================= */

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private DailyReportResponse toResponse(DailyReport report) {
        return new DailyReportResponse(
            report.getDate(),
            report.getTotalTasks(),
            report.getCompletedTasks(),
            report.getPendingTasks(),
            report.getCompletionPercentage(),
            report.getSummaryText()
        );
    }
}
