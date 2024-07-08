package org.example.controller;

import org.example.dto.Marker;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crm/")
public class ProductController extends Controller {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Обработка POST-запроса - Добавление нового продукта
     *
     * @param productDto полученный DTO-объект продукта
     * @return HTTP-ответ
     */
    @PostMapping(path = "/product/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addProduct(@Validated({Marker.OnCreate.class}) @RequestBody ProductDto productDto) {
        try {
            productService.add(productDto);
        } catch (Exception e) {
            return createErrorResponse(e);
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
            return createErrorResponse(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
