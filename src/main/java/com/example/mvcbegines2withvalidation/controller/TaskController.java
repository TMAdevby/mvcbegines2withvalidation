package com.example.mvcbegines2withvalidation.controller;

import com.example.mvcbegines2withvalidation.model.Status;
import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        // Если задачи нет, сервис выбросит исключение,
        // GlobalExceptionHandler перехватит его и вернет 404.
        // Нам больше не нужен if (task == null)!
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // ⚠️ ТЫ ЗАБЫЛ АННОТАЦИЮ @GetMapping ЗДЕСЬ! Добавляем её.
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        // Передаем строку в сервис, а безопасную проверку сделаем там
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.status(201).body(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        // Если id не существует, getTaskById внутри updateTask выбросит исключение -> 404
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // Если id не существует, сервис выбросит исключение -> 404
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Если дошли сюда, значит удалили успешно -> 204
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getTaskStatistics() {
        return ResponseEntity.ok(taskService.getTaskStatistics());
    }
}