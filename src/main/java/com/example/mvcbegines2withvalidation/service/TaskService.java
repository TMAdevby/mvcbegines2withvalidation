package com.example.mvcbegines2withvalidation.service;

import com.example.mvcbegines2withvalidation.exception.TaskNotFoundException;
import com.example.mvcbegines2withvalidation.model.Status;
import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(){
        return taskRepository.getAllTasks();
    }

    public Task getTaskById(Long id){
        return taskRepository.getTaskById(id).orElseThrow( () ->
                new TaskNotFoundException("Task number" + id + "is not found"));
    }

    public List<Task> getTasksByStatus(String status){
        return taskRepository.findAllByStatus(Status.valueOf(status));
    }

    public Task createTask(Task task){
        task.setDueDate(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task){
        Task currentTask = getTaskById(id);
        currentTask.setTitle(task.getTitle());
        currentTask.setDescription(task.getDescription());
        currentTask.setStatus(task.getStatus());
        currentTask.setDueDate(task.getDueDate());

        return taskRepository.save(currentTask);
    }
}
