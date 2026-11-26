package com.example.mvcbegines2withvalidation.model;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class Task {
    Long id;
    @NotBlank(message = "Название задачи не может быть пустым")
    @Size(max = 100, message = "Название задачи не может превышать 100 символов")
    String title;
    @Size(max = 500, message = "Описание не может превышать 500 символов")
    String description;
    Status status;
    @Min(value = 1, message = "Приоритет должен быть не менее 1")
    @Max(value = 5, message = "Приоритет должен быть не более 5")
    int priority;
    @FutureOrPresent(message = "Срок выполнения не может быть в прошлом")
    LocalDateTime dueDate;

    public Task() {
    }

    public Task(String title, String description, Status status, int priority, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Task(Long id, String title, String description, Status status, int priority, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
