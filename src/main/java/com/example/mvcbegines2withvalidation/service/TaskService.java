package com.example.mvcbegines2withvalidation.service;

import com.example.mvcbegines2withvalidation.exception.TaskNotFoundException;
import com.example.mvcbegines2withvalidation.model.Status;
import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(Long id) {
        return taskRepository.getTaskById(id).orElseThrow(() ->
                new TaskNotFoundException("Task number " + id + " is not found")
        );
    }

    public List<Task> getTasksByStatus(String status) {
        Status enumStatus = Status.fromString(status);
        if (enumStatus == null) {
            throw new IllegalArgumentException("Некорректный статус: '" + status + "'. Допустимые значения: NEW, IN_PROGRESS, DONE");
        }
        return taskRepository.findAllByStatus(enumStatus);
    }

    public Task createTask(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW); // Статус по умолчанию
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        // Если задачи нет, getTaskById сам выбросит TaskNotFoundException -> 404
        Task currentTask = getTaskById(id);

        currentTask.setTitle(taskDetails.getTitle());
        currentTask.setDescription(taskDetails.getDescription());
        currentTask.setStatus(taskDetails.getStatus());
        currentTask.setPriority(taskDetails.getPriority());
        currentTask.setDueDate(taskDetails.getDueDate());

        return taskRepository.save(currentTask);
    }

    public void deleteTask(Long id) {
        // Если задачи нет, getTaskById сам выбросит TaskNotFoundException -> 404
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    public Map<String, Long> getTaskStatistics() {
        return taskRepository.countTasksByStatus();
    }
}