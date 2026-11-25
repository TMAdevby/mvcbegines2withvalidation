package com.example.mvcbegines2withvalidation.service;

import com.example.mvcbegines2withvalidation.exception.TaskNotFoundException;
import com.example.mvcbegines2withvalidation.model.Status;
import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(Long id){
        return taskRepository.getTaskById(id).orElseThrow( () ->
                new TaskNotFoundException(String.valueOf(id)));
    }

    public List<Task> getTasksByStatus(String status){
        return taskRepository.findAllByStatus(Status.valueOf(status.toUpperCase()));
    }

    public Task createTask(Task task){
        if (task.getStatus() == null) {
            task.setStatus(Status.NEW); // Статус по умолчанию, если не указан
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task){
        Task currentTask = getTaskById(id);
        currentTask.setTitle(task.getTitle());
        currentTask.setDescription(task.getDescription());
        currentTask.setStatus(task.getStatus());
        currentTask.setPriority(task.getPriority());
        currentTask.setDueDate(task.getDueDate());

        return taskRepository.save(currentTask);
    }

    public void deleteTask(Long id) {
        // Хорошая практика: сначала проверяем, существует ли задача
        if (taskRepository.getTaskById(id).isEmpty()) {
            throw new TaskNotFoundException(String.valueOf(id));
        }
        taskRepository.deleteById(id);
    }

    public Map<String, Long> getTaskStatistics(){
        return taskRepository.countTasksByStatus();
    }
}
