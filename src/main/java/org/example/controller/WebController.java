package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * Контроллер приложения
 */
@Controller
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
    public String users(){
        return "forward:/user.html";
    }
    /**
     * Обработка GET-запроса - Начальная страница администратора
     * @return перенаправление на html
     */
    @GetMapping("/admins")
    public String admins(){
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
