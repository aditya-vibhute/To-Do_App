package com.example.To_do_App.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.To_do_App.entity.Task;
import com.example.To_do_App.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
        SELECT MAX(t.createdDate)
        FROM Task t
        WHERE t.user = :user
          AND t.createdDate < :today
    """)
    Optional<LocalDate> findLastActiveDate(
        @Param("user") User user,
        @Param("today") LocalDate today
    );
    
    List<Task> findByUserAndCreatedDate(User user, LocalDate date);

    long countByUserAndCreatedDate(User user, LocalDate date);

    long countByUserAndCreatedDateAndCompleted(
            User user, LocalDate date, boolean completed);
}

