package com.example.mvcbegines2withvalidation.repository;

import com.example.mvcbegines2withvalidation.model.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {

    ConcurrentHashMap<Long, Task> taskMap = new ConcurrentHashMap<>();

    AtomicLong idCounter = new AtomicLong(0);

    @PostConstruct
    public void init() {
        // Задача 1: Новая, высокий приоритет (1), дедлайн через 2 дня
        Task task1 = new Task(
                idCounter.incrementAndGet(), // Генерирует ID = 1
                "Изучить Spring MVC",
                "Разобраться с аннотациями @Controller, @GetMapping и @RequestParam",
                "NEW",
                1,
                LocalDateTime.now().plusDays(2)
        );
        taskMap.put(task1.getId(), task1);

        // Задача 2: В процессе, средний приоритет (2), дедлайн завтра
        Task task2 = new Task(
                idCounter.incrementAndGet(), // Генерирует ID = 2
                "Написать тесты для TaskService",
                "Покрыть юнит-тестами методы создания и обновления задач",
                "IN_PROGRESS",
                2,
                LocalDateTime.now().plusDays(1)
        );
        taskMap.put(task2.getId(), task2);

        // Задача 3: Завершена, низкий приоритет (3), дедлайн был вчера
        Task task3 = new Task(
                idCounter.incrementAndGet(), // Генерирует ID = 3
                "Обновить зависимости в pom.xml",
                "Проверить наличие уязвимостей и обновить версии библиотек",
                "DONE",
                3,
                LocalDateTime.now().minusDays(1)
        );
        taskMap.put(task3.getId(), task3);
    }

    public ArrayList<Task> getAllTasks(){
        return new ArrayList<Task>(taskMap.values());
    }

    public Optional<Task> getTaskById(Long id){
        return Optional.ofNullable(taskMap.get(id));
    }

    public List<Task> findAllByStatus(String status){

    }



}
