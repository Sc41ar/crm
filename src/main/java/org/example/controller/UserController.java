package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.dto.UserLoginDTO;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("crm/")
public class UserController extends Controller {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
            return createErrorResponse(e);
        }
        return ResponseEntity.status(OK).body("Success");
    }

    /**
     * Обработка POST-запроса - Верификация пользователя
     *
     * @param jwtString полученная строка пользователя для верификации
     * @return результат верификации
     */
    @PostMapping(path = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyUser(@CookieValue("jwt") String jwtString) {
        // Вызываем метод сервиса для верификации пользователя

        boolean isVerified = userService.jwtVerify(jwtString);

        if (!isVerified) {
            // Если пользователь не верифицирован, возвращаем статус 401 и сообщение об ошибке
            return ResponseEntity.status(UNAUTHORIZED).body("User not verified");
        }
        // Если пользователь успешно верифицирован, возвращаем статус 200 и сообщение об успехе
        return ResponseEntity.status(OK).body("User verified successfully");
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        String jwt = "";

        jwt = userService.userLogin(userLoginDTO);

        // Если JWT равен null, то возвращаем ошибку с сообщением "Неверное имя пользователя или пароль"
        if (jwt == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> errorResponse = new HashMap<>();
                // Добавляем сообщение об ошибке в русском языке
                errorResponse.put("error", "Неверное имя пользователя или пароль");
                String json = mapper.writeValueAsString(errorResponse);
                // Возвращаем HTTP-ответ с кодом 401 Unauthorized и сообщением об ошибке в формате JSON
                return json;
            } catch (JsonProcessingException ex) {
                // Если произошла ошибка при формировании JSON, возвращаем простое сообщение об ошибке
                return "Неизвестная ошибка";
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
        response.setStatus(200);
        return "succeed";
    }


}

