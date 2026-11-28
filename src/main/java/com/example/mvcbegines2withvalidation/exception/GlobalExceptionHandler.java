package com.example.mvcbegines2withvalidation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Обработка "Задача не найдена" -> 404
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 2. Обработка "Неверные данные от клиента" -> 400 (НОВЫЙ МЕТОД)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 3. Обработка любых других непредвиденных ошибок -> 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
    }

    // 4. НОВЫЙ МЕТОД: Обработка ошибок валидации (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Проходим по всем полям, которые не прошли валидацию
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Возвращаем 400 Bad Request и карту ошибок: {"title": "Название не может быть пустым", "priority": "Приоритет от 1 до 5"}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 5. НОВЫЙ МЕТОД: Обработка нашего кастомного исключения бизнес-логики
    @ExceptionHandler(TaskValidationFailedException.class)
    public ResponseEntity<String> handleTaskValidationFailedException(TaskValidationFailedException ex) {
        // Возвращаем 400 Bad Request, так как ошибка вызвана некорректными данными по бизнес-правилам
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}