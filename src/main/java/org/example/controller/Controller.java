package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    /**
     * Формирование HTTP-ответа об ошибке
     *
     * @param e полученное исключение
     * @return HTTP-ответ
     */
    public ResponseEntity<String> createErrorResponse(Exception e) {
        String errorMessage = e.getMessage();
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    /**
     * Обработка ошибок валидации
     *
     * @param e ошибка валидации ConstraintViolationException
     * @return HTTP-ответ об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationConstraintViolationExceptions(ConstraintViolationException e) {
        Map<String, String> errorResponse = new HashMap<>();
        e.getConstraintViolations().forEach((error) -> {
            String errorMessage = error.getMessage();
            String errorField = error.getPropertyPath().toString();
            errorField = errorField.substring(errorField.lastIndexOf(".") + 1);
            errorResponse.put(errorField, errorMessage);
        });
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    /**
     * Обработка ошибок валидации
     *
     * @param e ошибка валидации MethodArgumentNotValidException
     * @return HTTP-ответ об ошибке
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorResponse = new HashMap<>();
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError error : fieldErrors) {
            String errorField = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.put(errorField, errorMessage);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }


}
