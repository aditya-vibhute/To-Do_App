package com.example.To_do_App.entity;

import java.time.LocalDate;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Access(AccessType.FIELD)
@Table(
    name = "daily_reports",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "report_date"})
    }
)
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ REQUIRED

    @Column(name = "report_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private long totalTasks;

    @Column(nullable = false)
    private long completedTasks;

    @Column(nullable = false)
    private long pendingTasks;

    @Column(nullable = false)
    private double completionPercentage;

    @Column(length = 255)
    private String summaryText;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // ===== getters & setters =====
}
