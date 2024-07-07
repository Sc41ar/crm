package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.Marker;
import org.example.dto.ProductDealDto;
import org.example.service.ProductDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crm/")
public class ProductDealController extends Controller {
    private final ProductDealService productDealService;

    @Autowired
    public ProductDealController(ProductDealService productDealService) {
        this.productDealService = productDealService;
    }

    /**
     * Обработка POST-запроса - Добавление новоо продукта в сделку
     *
     * @param productDealDto полученный DTO-объект продукта сделки
     * @return HTTP-ответ
     */
    @PostMapping(path = "/product-deal/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProductDeal(@Validated({Marker.OnCreate.class}) @RequestBody ProductDealDto productDealDto) {
        try {
            productDealService.add(productDealDto);
        } catch (Exception e) {
            return createErrorResponse(e);
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
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
