package org.example.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * Контроллер приложения
 */
@RestController
@Validated
@RequestMapping("crm/")
public class WebController {
    private final UserService userService;
    private final ClientService clientService;
    private final CompanyService companyService;
    private final ProductService productService;
    private final DealService dealService;
    private final ProductDealService productDealService;

    @Autowired
    public WebController(UserService userService, ClientService clientService,
                         CompanyService companyService, ProductService productService,
                         DealService dealService, ProductDealService productDealService) {
        this.userService = userService;
        this.clientService = clientService;
        this.companyService = companyService;
        this.productService = productService;
        this.dealService = dealService;
        this.productDealService = productDealService;
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
                ResponseEntity.status(BAD_REQUEST).body("Unknown error");
            }
            return ResponseEntity.status(BAD_REQUEST).body(json);
        }
        return ResponseEntity.status(OK).body("Success");
    }

    /**
     * Обработка POST-запроса - Верификация пользователя
     * Я НЕ ПРОВЕРЯЛ ПОКА, ЭТОТ МЕТОД СГЕНЕРИРОВАН
     *
     * @param jwtString полученный DTO-объект пользователя для верификации
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

    /**
     * Обработка POST-запроса - Добавление нового клиента
     *
     * @param clientDto полученный DTO-объект клиента
     * @return HTTP-ответ
     */
    @Validated({Marker.OnCreate.class})
    @PostMapping(path = "/client/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addClient(@Valid @RequestBody ClientDto clientDto) {
        try {
            clientService.add(clientDto);
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
    @Validated(Marker.OnUpdate.class)
    @PutMapping(path = "/client/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClient(@Valid @RequestBody ClientDto clientDto) {
        try {
            clientService.update(clientDto);
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

    /**
     * Обработка POST-запроса - Добавление нового продукта
     *
     * @param productDto полученный DTO-объект продукта
     * @return HTTP-ответ
     */
    @Validated({Marker.OnCreate.class})
    @PostMapping(path = "/product/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto) {
        try {
            productService.add(productDto);
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

    /**
     * Обработка GET-запроса - Получение списка всех продуктов
     *
     * @return список всех продуктов
     */
    @GetMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDto> getProduct() {
        return productService.findAll();
    }

    /**
     * Обработка PUT-запроса - Обновление данных о продукте
     *
     * @param productDto DTO с заполненными полями для обновления
     * @return HTTP-ответ
     */
    @PutMapping(path = "/product/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProduct(@Validated(Marker.OnUpdate.class) @RequestBody ProductDto productDto) {
        try {
            productService.update(productDto);
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

    /**
     * Обработка POST-запроса - Добавление новой сделки
     *
     * @param dealDto полученный DTO-объект сделки
     * @return HTTP-ответ
     */
    @Validated({Marker.OnCreate.class})
    @PostMapping(path = "/deal/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addDeal(@Valid @RequestBody DealDto dealDto) {
        try {
            dealService.add(dealDto);
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

    /**
     * Обработка GET-запроса - Получение списка всех сделок
     *
     * @return список всех сделок
     */
    @GetMapping(path = "/deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DealDto> getDeal() {
        return dealService.findAll();
    }

    /**
     * Обработка GET-запроса - Получение списка сделок сотрудника
     *
     * @return список всех сделок сотрудника
     */
    @GetMapping(path = "/deal/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DealDto> getDealByUsername(@Valid @RequestBody DealDto dealDto) {
        return dealService.findByUsername(dealDto.getUserUsername());
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

    /**
     * Обработка POST-запроса - Добавление новоо продукта в сделку
     *
     * @param productDealDto полученный DTO-объект продукта сделки
     * @return HTTP-ответ
     */
    @Validated({Marker.OnCreate.class})
    @PostMapping(path = "/product-deal/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductDeal(@Valid @RequestBody ProductDealDto productDealDto) {
        try {
            productDealService.add(productDealDto);
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

    /**
     * Обработка GET-запроса - Получение списка всех продуктов сделок
     *
     * @return список всех продуктов сделок
     */
    @GetMapping(path = "/product-deal", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDealDto> getProductDeal() {
        return productDealService.findAll();
    }

    /**
     * Обработка GET-запроса - Получение списка продуктов конкретной сделки
     *
     * @return список всех продуктов сделки
     */
    @GetMapping(path = "/product-deal/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDealDto> getProductDealByDealName(@Valid @RequestBody ProductDealDto productDealDto) {
        return productDealService.findByDealName(productDealDto.getDealName());
    }

    /**
     * Обработка PUT-запроса - Обновление данных о продукте сделки
     *
     * @param productDealDto DTO с заполненными полями для обновления
     * @return HTTP-ответ
     */
    @PutMapping(path = "/product-deal/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProductDeal(@Validated(Marker.OnUpdate.class) @RequestBody ProductDealDto productDealDto) {
        try {
            productDealService.update(productDealDto);
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

    /**
     * Обработка ошибок валидации
     *
     * @param e ошибка валидации ConstraintViolationException
     * @return HTTP-ответ об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationConstraintViolationExceptions(ConstraintViolationException e) {
        Map<String, String> errorResponse = new HashMap<>();
        e.getConstraintViolations().forEach((error) -> {
            String errorMessage = error.getMessage();
            String errorField = error.getPropertyPath().toString();
            errorField = errorField.substring(errorField.lastIndexOf(".") + 1);
            errorResponse.put(errorField, errorMessage);
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

    /**
     * Обработка ошибок валидации
     *
     * @param e ошибка валидации MethodArgumentNotValidException
     * @return HTTP-ответ об ошибке
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errorResponse = new HashMap<>();
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError error : fieldErrors) {
            String errorField = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.put(errorField, errorMessage);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException ex) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }
}

