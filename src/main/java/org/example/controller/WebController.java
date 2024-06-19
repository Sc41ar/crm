package org.example.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * Контроллер приложения
 */
@Controller
@RequestMapping("crm/")
public class WebController {
    /**
     * Обработка GET-запроса - Начальная страница пользователя
     */
    @GetMapping("/users")
    public String users(){
        return "forward:/user.html";
    }
    /**
     * Обработка GET-запроса - Начальная страница администратора
     */
    @GetMapping("/admins")
    public String admins(){
        return "forward:/admin.html";
    }
}
