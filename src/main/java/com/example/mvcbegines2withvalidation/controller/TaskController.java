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
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) { // Используем Long (обертка), а не long
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build(); // Возвращает 404 Not Found без тела
        }
        return ResponseEntity.ok(task); // Возвращает 200 OK и задачу в теле
    }

    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
         Status enumStatus = Status.fromString(status);
         if (enumStatus == null) return ResponseEntity.badRequest().build(); // 400 Bad Request

        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(201).body(createdTask); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        // Предполагается, что сервис вернет null, если задачи с таким id нет
        Task updatedTask = taskService.updateTask(id, taskDetails);
        if (updatedTask == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(updatedTask); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        // 204 No Content означает "успешно, но возвращать нечего"
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getTaskStatistics() {
        return ResponseEntity.ok(taskService.getTaskStatistics());
    }
}
