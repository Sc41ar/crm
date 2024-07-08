package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.DealDto;
import org.example.dto.Marker;
import org.example.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crm/")
public class DealController extends Controller {
    private final DealService dealService;

    @Autowired
    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    /**
     * Обработка POST-запроса - Добавление новой сделки
     *
     * @param dealDto полученный DTO-объект сделки
     * @return HTTP-ответ
     */
    @PostMapping(path = "/deal/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addDeal(@Validated({Marker.OnCreate.class}) @RequestBody DealDto dealDto) {
        try {
            dealService.add(dealDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }


    /**
     * Обработка GET-запроса - Получение списка сделок сотрудника
     *
     * @param username логин сотрудника
     * @return список сделок конкретного сотрудника
     */
    @GetMapping(path = "/deal/username", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DealDto> getDealByUsername(@Valid @RequestParam("username") String username) {
        return dealService.findByUsername(username);
    }

    /**
     * Обработка PUT-запроса - Обновление данных о сделке
     *
     * @param dealDto DTO с заполненными полями для обновления
     * @return HTTP-ответ
     */
    @PutMapping(path = "/deal/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDeal(@Validated(Marker.OnUpdate.class) @RequestBody DealDto dealDto) {
        try {
            dealService.update(dealDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
