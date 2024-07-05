package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.dto.ProductDto;
import org.example.entity.ProductEntity;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса продуктов
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    /**
     * DTO-объекты продуктов
     */
    private final ProductDto firstProductDto = ProductDto.builder().id(1L).name(RandomString.make(10))
            .unit(RandomString.make(3)).unitPrice(BigDecimal.valueOf(Math.random() * 100)).build();
    private final ProductDto secondProductDto = ProductDto.builder().name(RandomString.make(10))
            .unit(RandomString.make(3)).unitPrice(BigDecimal.valueOf(Math.random() * 100)).build();
    /**
     * Entity-объекты продуктов
     */
    private final ProductEntity firstProductEntity = ProductEntity.builder().id(firstProductDto.getId())
            .name(firstProductDto.getName()).unit(firstProductDto.getUnit())
            .unitPrice(firstProductDto.getUnitPrice()).build();
    private final ProductEntity secondProductEntity = ProductEntity.builder().name(secondProductDto.getName())
            .unit(secondProductDto.getUnit()).unitPrice(secondProductDto.getUnitPrice()).build();
    /**
     * Мок объект репозитория продуктов
     */
    @Mock
    private ProductRepository productRepository;
    /**
     * Сервис продуктов
     */
    @InjectMocks
    private ProductService productService;

    /**
     * Тест получения списка всех продуктов
     */
    @Test
    void findAll() {
        List<ProductEntity> listEntity = new ArrayList<>();
        listEntity.add(firstProductEntity);
        listEntity.add(secondProductEntity);
        given(productRepository.findAll()).willReturn(listEntity);
        List<ProductDto> allCompanyList = productService.findAll();
        assertEquals(2, allCompanyList.size());
        assertEquals(firstProductDto, allCompanyList.get(0));
        assertEquals(secondProductDto, allCompanyList.get(1));
        verify(productRepository, Mockito.times(1)).findAll();
    }

    /**
     * Тест добавления продукта
     */
    @Test
    void add() {
        productService.add(secondProductDto);
        verify(productRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(secondProductEntity)));
    }

    /**
     * Тест обновления записи о продукте
     */
    @Test
    void update() {
        ProductDto updateDto = ProductDto.builder().id(firstProductDto.getId())
                .name(RandomString.make(8)).build();
        ProductEntity updateEntity = ProductEntity.builder().id(firstProductDto.getId())
                .name(updateDto.getName()).unit(firstProductDto.getUnit())
                .unitPrice(firstProductDto.getUnitPrice()).build();
        Optional<ProductEntity> entityOptional = Optional.of(firstProductEntity);
        given(productRepository.findById(firstProductDto.getId())).willReturn(entityOptional);
        assertDoesNotThrow(() -> productService.update(updateDto));
        verify(productRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(updateEntity)));
        verify(productRepository, Mockito.times(1)).findById(firstProductDto.getId());

    }

    /**
     * Тест неудачной попытки обновления несуществующей записи
     */
    @Test
    void updateNonExistentRecord() {
        Optional<ProductEntity> emptyOptional = Optional.empty();
        given(productRepository.findById(firstProductDto.getId())).willReturn(emptyOptional);
        Assertions.assertThrows(Exception.class, () -> productService.update(firstProductDto));
        verify(productRepository, Mockito.times(0)).save(any(ProductEntity.class));
        verify(productRepository, Mockito.times(1)).findById(firstProductDto.getId());
    }
}
