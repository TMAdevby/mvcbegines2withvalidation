package com.example.mvcbegines2withvalidation.controller;

import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/tasks")
public class TaskContrller {

    private TaskService taskService;

    public TaskContrller(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<Task> getAllTasks(){

    }
}
