package org.example.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.dto.DealDto;
import org.example.dto.Marker;
import org.example.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * @return список всех сделок сотрудника
     */
    @GetMapping(path = "/deal/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DealDto> getDealByUsername(@RequestParam String username) {
        List<DealDto> list;
        list = dealService.findByUsername(username);
        if (list.isEmpty())
            list = dealService.findByEmail(username);
        return list;
    }

    /**
     * Обработка GET-запроса - формирование отчета о сделках за конкретный месяц
     *
     * @param monthYear строка месяц-год
     * @return HTTP-ответ - с файлом или пустой в случае ошибки
     */
    @GetMapping(path = "/deal/report", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FileSystemResource> createReport(@RequestParam("month_year") String monthYear) {
        ResponseEntity<FileSystemResource> response;
        Workbook workbook = dealService.createXLSX(monthYear);
        try {
            File tempFile = File.createTempFile("temp", ".xlsx");
            FileOutputStream fos = new FileOutputStream(tempFile);
            workbook.write(fos);
            fos.close();
            FileSystemResource resource = new FileSystemResource(tempFile);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=deal.xlsx");
            response = ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return response;
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
