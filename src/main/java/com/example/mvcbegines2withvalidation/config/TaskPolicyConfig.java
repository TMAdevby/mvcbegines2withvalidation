package com.example.mvcbegines2withvalidation.config;


import com.example.mvcbegines2withvalidation.exception.TaskValidationFailedException;
import com.example.mvcbegines2withvalidation.model.Task;
import com.example.mvcbegines2withvalidation.policy.TaskValidationPolicy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Это класс-фабрика бинов
public class TaskPolicyConfig {

    // БИН 1: Мягкая политика.
    // Создастся, ТОЛЬКО если в YAML app.task.strict-mode = false
    // ИЛИ если этой настройки в YAML вообще нет (matchIfMissing = true)
    @Bean
    @ConditionalOnProperty(name = "app.task.strict-mode", havingValue = "false", matchIfMissing = true)
    public TaskValidationPolicy lenientPolicy() {
        return new TaskValidationPolicy() {
            @Override
            public void validate(Task task) {
                System.out.println("[DEV] Мягкая проверка: задача '" + task.getTitle() + "' пропущена.");
                // Никаких исключений не бросаем
            }
        };
    }

    // БИН 2: Строгая политика.
    // Создастся, ТОЛЬКО если в YAML app.task.strict-mode = true
    // Обрати внимание: мы можем внедрить TaskAppProperties прямо в метод создания бина!
    @Bean
    @ConditionalOnProperty(name = "app.task.strict-mode", havingValue = "true")
    public TaskValidationPolicy strictPolicy(TaskAppProperties properties) {
        return new TaskValidationPolicy() {
            @Override
            public void validate(Task task) {
                if (task.getTitle() != null && task.getTitle().length() < properties.getMinTitleLength()) {
                    throw new TaskValidationFailedException(
                            "В строгом режиме название должно быть минимум " + properties.getMinTitleLength() + " символов!"
                    );
                }
                System.out.println("[PROD] Строгая проверка пройдена для: " + task.getTitle());
            }
        };
    }
}
