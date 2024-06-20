package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер приложения
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.POST, RequestMethod.PUT}, allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("crm/")
public class WebController {
    private final UserService service;

    @Autowired
    public WebController(UserService service) {
        this.service = service;
    }

    /**
     * Обработка GET-запроса - Начальная страница пользователя
     * @return перенаправление на html
     */
    @GetMapping("/users")
    public String users() {
        return "forward:/user.html";
    }

    /**
     * Обработка GET-запроса - Начальная страница администратора
     * @return перенаправление на html
     */
    @GetMapping("/admins")
    public String admins() {
        return "forward:/admin.html";
    }

    /**
     * Обработка POST-запроса - Страница регистрации
     * @param userDto полученный DTO-объект регистрируемого пользователя
     * @return результат регистрации
     */
    @PostMapping(path = "/reg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            service.registerNewUser(userDto);
        } catch (Exception e) {
            String errorMessage = e.getLocalizedMessage();
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
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
