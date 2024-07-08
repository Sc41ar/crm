package org.example.controller;

import org.example.dto.CompanyDto;
import org.example.dto.Marker;
import org.example.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crm/")
public class CompanyController extends Controller {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Обработка POST-запроса - Добавление новой компании
     *
     * @param companyDto полученный DTO-объект компании
     * @return HTTP-ответ
     */
    @PostMapping(path = "/company/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCompany(@Validated({Marker.OnCreate.class}) @RequestBody CompanyDto companyDto) {
        try {
            companyService.add(companyDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    /**
     * Обработка GET-запроса - Получение списка всех компаний
     *
     * @return список всех компаний
     */
    @GetMapping(path = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompanyDto> getCompany() {
        return companyService.findAll();
    }

    /**
     * Обработка PUT-запроса - Обновление данных о компании
     *
     * @param companyDto DTO с заполненными полями для обновления
     * @return HTTP-ответ
     */
    @PutMapping(path = "/company/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCompany(@Validated(Marker.OnUpdate.class) @RequestBody CompanyDto companyDto) {
        try {
            companyService.update(companyDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
