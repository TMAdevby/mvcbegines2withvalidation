package com.example.mvcbegines2withvalidation.config;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data // Генерирует геттеры и сеттеры (или сделай их вручную)
@Component // Делаем этот класс бином, чтобы Spring его видел
@Validated // Включаем валидацию ПОЛЕЙ этого класса при старте приложения!
@ConfigurationProperties(prefix = "app.task") // Spring возьмет все свойства, начинающиеся с "app.task"
public class TaskAppProperties {

    // В YAML это будет: app.task.strict-mode: true
    private boolean strictMode;

    // В YAML: app.task.min-title-length: 5
    @Min(value = 3, message = "Длина не может быть меньше 3")
    @Max(value = 20, message = "Длина не может быть больше 20")
    private int minTitleLength = 5;
}
