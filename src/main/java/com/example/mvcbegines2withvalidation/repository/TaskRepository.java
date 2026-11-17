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
import java.util.stream.Collectors;

@Repository
public class TaskRepository {

    ConcurrentHashMap<Long, Task> taskMap = new ConcurrentHashMap<>();

    AtomicLong idCounter = new AtomicLong(0);

    @PostConstruct
    public void init() {
        Task task1 = new Task(
                idCounter.incrementAndGet(),
                "Изучить Spring MVC",
                "Разобраться с аннотациями @Controller, @GetMapping и @RequestParam",
                "NEW",
                1,
                LocalDateTime.now().plusDays(2)
        );
        taskMap.put(task1.getId(), task1);

        Task task2 = new Task(
                idCounter.incrementAndGet(),
                "Написать тесты для TaskService",
                "Покрыть юнит-тестами методы создания и обновления задач",
                "IN_PROGRESS",
                2,
                LocalDateTime.now().plusDays(1)
        );
        taskMap.put(task2.getId(), task2);

        Task task3 = new Task(
                idCounter.incrementAndGet(),
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
        List<Task> list = getAllTasks();
        return list.stream()
                .filter(t-> t.getStatus() == status)
                .collect(Collectors.toList());

    }

    public Task save(Task task){
        if(task.getId() == null || task.getId() == 0L){
            task.setId(idCounter.incrementAndGet());
        }
        taskMap.put(task.getId(), task);
        return task;
    }



}
