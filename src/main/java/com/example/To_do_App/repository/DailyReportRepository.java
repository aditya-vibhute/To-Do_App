package com.example.To_do_App.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.To_do_App.entity.DailyReport;
import com.example.To_do_App.entity.User;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    Optional<DailyReport> findByUserAndDate(User user, LocalDate date);

    List<DailyReport> findByUserOrderByDateDesc(User user);

    List<DailyReport> findByUser(User user);

    boolean existsByUserAndDate(User user, LocalDate date);
}

