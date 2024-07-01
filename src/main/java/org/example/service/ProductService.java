package org.example.service;

import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис продуктов
 */
@Service
public class ProductService implements ServiceInterface<ProductDto> {
    /**
     * Репозиторий для записей о продуктах
     */
    @Autowired
    ProductRepository productRepository;

    /**
     * Добавление нового клиента
     *
     * @param productDto DTO-объект продукта
     */
    @Override
    public void add(ProductDto productDto) {
        ProductEntity productEntity = ProductEntity.builder().name(productDto.getName())
                .unit(productDto.getUnit()).unitPrice(productDto.getUnitPrice()).build();
        productRepository.save(productEntity);
    }

    /**
     * Обновление записи о продукте
     *
     * @param productDto DTO с заполненными полями для обновления
     * @throws Exception не найдена запись по id
     */
    @Override
    public void update(ProductDto productDto) throws Exception {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productDto.getId());
        if (productEntityOptional.isEmpty()) {
            throw new Exception("Нет записи для обновления");
        }
        ProductEntity productEntity = productEntityOptional.get();
        if (productDto.getName() != null && !productDto.getName().trim().isEmpty()) {
            productEntity.setName(productDto.getName());
        }
        if (productDto.getUnit() != null && !productDto.getUnit().trim().isEmpty()) {
            productEntity.setUnit(productDto.getUnit());
        }
        if (productDto.getUnitPrice() != null) {
            productEntity.setUnitPrice(productDto.getUnitPrice());
        }
        productRepository.save(productEntity);
    }

    /**
     * Получение списка всех продуктов
     *
     * @return список из DTO-объектов продуктов
     */
    @Override
    public List<ProductDto> findAll() {
        List<ProductEntity> entityList = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity entity : entityList) {
            productDtos.add(ProductDto.builder().id(entity.getId()).name(entity.getName())
                    .unit(entity.getUnit()).unitPrice(entity.getUnitPrice()).build());
        }
        return productDtos;
    }
}
