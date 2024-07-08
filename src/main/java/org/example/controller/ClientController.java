package org.example.controller;

import org.example.dto.ClientDto;
import org.example.dto.Marker;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crm/")
public class ClientController extends Controller {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Обработка POST-запроса - Добавление нового клиента
     *
     * @param clientDto полученный DTO-объект клиента
     * @return HTTP-ответ
     */
    @PostMapping(path = "/client/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addClient(@Validated({Marker.OnCreate.class}) @RequestBody ClientDto clientDto) {
        try {
            clientService.add(clientDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    /**
     * Обработка GET-запроса - Получение списка всех клиентов
     *
     * @return список всех клиентов
     */
    @GetMapping(path = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClientDto> getClient() {
        return clientService.findAll();
    }

    /**
     * Обработка PUT-запроса - Обновление данных о клиенте
     *
     * @param clientDto DTO с заполненными полями для обновления
     * @return HTTP-ответ
     */
    @PutMapping(path = "/client/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClient(@Validated(Marker.OnUpdate.class) @RequestBody ClientDto clientDto) {
        try {
            clientService.update(clientDto);
        } catch (Exception e) {
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
