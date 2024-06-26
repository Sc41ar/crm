package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.example.service.CompanyService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер приложения
 */
@RestController
@RequestMapping("crm/")
public class WebController {
    private final UserService userService;
    private final CompanyService companyService;

    @Autowired
    public WebController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    /**
     * Обработка POST-запроса - Страница регистрации
     *
     * @param userDto полученный DTO-объект регистрируемого пользователя
     * @return результат регистрации
     */
    @PostMapping(path = "/reg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.registerNewUser(userDto);
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

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserDto userDto, HttpServletResponse response) {
        String jwt = "";

        jwt = userService.userLogin(userDto);

        // Если JWT равен null, то возвращаем ошибку с сообщением "Неверное имя пользователя или пароль"
        if (jwt == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> errorResponse = new HashMap<>();
                // Добавляем сообщение об ошибке в русском языке
                errorResponse.put("error", "Неверное имя пользователя или пароль");
                String json = mapper.writeValueAsString(errorResponse);
                // Возвращаем HTTP-ответ с кодом 401 Unauthorized и сообщением об ошибке в формате JSON
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
            } catch (JsonProcessingException ex) {
                // Если произошла ошибка при формировании JSON, возвращаем простое сообщение об ошибке
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неизвестная ошибка");
            }
        }
        // Создаем новый Cookie с именем "jwt" и значением JWT токена
        Cookie cookie = new Cookie("jwt", jwt);
        // Устанавливаем время жизни Cookie в 900 секунд (15 минут)
        cookie.setMaxAge(900);
        // Устанавливаем флаг HttpOnly, чтобы Cookie не был доступен через JavaScript
        cookie.setHttpOnly(true);
        // Устанавливаем путь, на котором Cookie будет доступен (в данном случае - корневой путь)
        cookie.setPath("/");
        // Добавляем Cookie в HTTP-ответ
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Обработка POST-запроса - Добавление новой компании
     *
     * @param companyDto полученный DTO-объект компании
     * @param result
     * @return
     */
    @PostMapping(path = "/company/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCompany(@Valid @RequestBody CompanyDto companyDto, BindingResult result) {
        if (result.hasErrors()) {   //проверка, есть ли ошибки валидации
            Map<String, String> errorResponse = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errorResponse.put(fieldName, errorMessage);
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
        try {
            companyService.add(companyDto);
        } catch (Exception e) {
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
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
