package com.example.To_do_App.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.To_do_App.dto.response.TaskResponse;
import com.example.To_do_App.entity.Task;
import com.example.To_do_App.entity.User;
import com.example.To_do_App.repository.TaskRepository;
import com.example.To_do_App.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse createTask(String email, String title) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setUser(user);
        task.setCreatedDate(LocalDate.now());

        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    public List<TaskResponse> todayTasks(String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUserAndCreatedDate(
                user, LocalDate.now()).stream()
                .map(this::toResponse)
                .toList();
    }

    public void toggle(Long taskId, String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    
    @Transactional
    public void delete(Long taskId, String email) {

        System.out.println("Delete requested for taskId=" + taskId +
                        ", user=" + email);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        System.out.println("Task belongs to userId=" +
                        task.getUser().getId());

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden: not owner");
        }

        taskRepository.delete(task);
        System.out.println("Task deleted successfully");
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.isCompleted(),
            task.getCreatedDate()
        );
    }


}
