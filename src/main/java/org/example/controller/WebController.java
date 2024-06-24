package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.dto.JWTVerifyDTO;
import org.example.dto.UserDto;
import org.example.dto.UserLoginDTO;
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
@RequestMapping("crm/")
public class WebController {
    private final UserService service;

    @Autowired
    public WebController(UserService service) {
        this.service = service;
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

    /**
     * Обработка POST-запроса - Верификация пользователя
     * Я НЕ ПРОВЕРЯЛ ПОКА, ЭТОТ МЕТОД СГЕНЕРИРОВАН
     * @param jwtDto полученный DTO-объект пользователя для верификации
     * @return результат верификации
     */
    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyUser(@Valid @RequestBody JWTVerifyDTO jwtDto) {
        try {
            // Вызываем метод сервиса для верификации пользователя
            boolean isVerified = service.jwtVerify(jwtDto);

            if (isVerified) {
                // Если пользователь успешно верифицирован, возвращаем статус 200 и сообщение об успехе
                return ResponseEntity.status(HttpStatus.OK).body("User verified successfully");
            } else {
                // Если пользователь не верифицирован, возвращаем статус 401 и сообщение об ошибке
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not verified");
            }
            //TODO убрать try\catch мб
        } catch (Exception e) {
            // Если произошла ошибка, получаем сообщение об ошибке
            String errorMessage = e.getLocalizedMessage();
            // Создаем объект Map для хранения сообщения об ошибке
            Map<String, String> errorResponse = new HashMap<>();
            // Добавляем сообщение об ошибке в объект Map
            errorResponse.put("error", errorMessage);
            // Создаем объект ObjectMapper для преобразования объекта Map в JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            try {
                // Преобразуем объект Map в JSON
                json = mapper.writeValueAsString(errorResponse);
            } catch (JsonProcessingException ex) {
                // Если произошла ошибка при преобразовании в JSON, возвращаем простое сообщение об ошибке
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неизвестная ошибка");
            }
            // Возвращаем HTTP-ответ с кодом 400 Bad Request и сообщением об ошибке в формате JSON
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        String jwt = "";

        jwt = service.userLogin(userLoginDTO);

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
}
