package com.example.To_do_App.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_do_App.dto.request.TaskRequest;
import com.example.To_do_App.dto.response.TaskResponse;
import com.example.To_do_App.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ✅ Create a task
    @PostMapping
    public TaskResponse createTask(
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {

        String email = authentication.getName(); // ✅ CORRECT
        return taskService.createTask(email, request.getTitle());
    }

    // ✅ Get today's tasks
    @GetMapping("/today")
    public List<TaskResponse> getTodayTasks(Authentication authentication) {

        String email = authentication.getName(); // ✅ CORRECT
        System.out.println("Fetching tasks for: " + email);
        return taskService.todayTasks(email);
    }

    // ✅ Toggle task completion
    @PutMapping("/{taskId}/toggle")
    public void toggleTask(
            @PathVariable Long taskId,
            Authentication authentication) {

        String email = authentication.getName(); // ✅ CORRECT
        taskService.toggle(taskId, email);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(
            @PathVariable Long taskId,
            Authentication authentication) {

        String email = authentication.getName();
        taskService.delete(taskId, email);
    }
}
