package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.dto.ProductDealDto;
import org.example.entity.DealEntity;
import org.example.entity.ProductDealEntity;
import org.example.entity.ProductDealPK;
import org.example.entity.ProductEntity;
import org.example.repository.DealRepository;
import org.example.repository.ProductDealRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса продуктов сделок
 */
@ExtendWith(MockitoExtension.class)
public class ProductDealServiceTest {
    /**
     * Entity-объекты сделки для внешнего ключа
     */
    private final DealEntity firstDealEntity = DealEntity.builder().id(1L)
            .name(RandomString.make(10)).totalCost(BigDecimal.ZERO).build();
    private final DealEntity secondDealEntity = DealEntity.builder().id(2L).name(RandomString.make(10))
            .totalCost(BigDecimal.ZERO).build();
    /**
     * Entity-объект продукта для внешнего ключа
     */
    private final ProductEntity productEntity = ProductEntity.builder().id(2L)
            .name(RandomString.make(10)).unit("кг").unitPrice(BigDecimal.valueOf(100)).build();
    /**
     * Первый DTO-объект продукта сделки
     */
    private final ProductDealDto firstProductDealDto = ProductDealDto.builder().productId(productEntity.getId())
            .dealId(firstDealEntity.getId()).dealName(firstDealEntity.getName()).cost(new BigDecimal("20.00"))
            .productName(productEntity.getName()).quantity(BigDecimal.valueOf(200)).unit("гр").build();
    /**
     * Первый Entity-объект продукта сделки
     */
    private final ProductDealEntity firstProductDealEntity = ProductDealEntity.builder()
            .id(new ProductDealPK(firstDealEntity, productEntity))
            .quantity(firstProductDealDto.getQuantity()).unit(firstProductDealDto.getUnit())
            .cost(new BigDecimal("20.00")).build();
    /**
     * Второй DTO-объект продукта сделки
     */
    private final ProductDealDto secondProductDealDto = ProductDealDto.builder().productId(productEntity.getId())
            .dealId(secondDealEntity.getId()).dealName(secondDealEntity.getName()).cost(BigDecimal.ZERO)
            .productName(productEntity.getName()).quantity(BigDecimal.valueOf(3)).unit("м").build();
    /**
     * Второй Entity-объект продукта сделки
     */
    private final ProductDealEntity secondProductDealEntity = ProductDealEntity.builder()
            .id(new ProductDealPK(secondDealEntity, productEntity)).cost(BigDecimal.ZERO)
            .quantity(secondProductDealDto.getQuantity()).unit(secondProductDealDto.getUnit()).build();
    /**
     * Мок объект репозитория продуктов сделки
     */
    @Mock
    private ProductDealRepository productDealRepository;
    /**
     * Мок объект репозитория сделок
     */
    @Mock
    private DealRepository dealRepository;
    /**
     * Мок объект репозитория продуктов
     */
    @Mock
    private ProductRepository productRepository;
    /**
     * Сервис продуктов сделок
     */
    @InjectMocks
    private ProductDealService productDealService;

    /**
     * Тест добавления продукта сделки
     */

    @Test
    void add() {
        Optional<DealEntity> dealEntityOptional = Optional.of(firstDealEntity);
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        given(dealRepository.findByName(firstDealEntity.getName())).willReturn(dealEntityOptional);
        given(productRepository.findByName(productEntity.getName())).willReturn(productEntityOptional);
        assertDoesNotThrow(() -> productDealService.add(firstProductDealDto));
        verify(productDealRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(firstProductDealEntity)));
        verify(dealRepository, Mockito.times(1)).findByName(firstDealEntity.getName());
        verify(productRepository, Mockito.times(1)).findByName(productEntity.getName());
        verify(dealRepository, Mockito.times(1))
                .updateTotalCost(firstDealEntity.getId(), firstProductDealEntity.getCost());
    }

    /**
     * Тест неудачной попытки добавления продукта сделки с несуществующим внешним ключом продукта
     */
    @Test
    void addWrongForeignKey() {
        Optional<ProductEntity> productEntityOptional = Optional.empty();
        given(productRepository.findByName(productEntity.getName())).willReturn(productEntityOptional);
        Assertions.assertThrows(Exception.class, () -> productDealService.add(firstProductDealDto));
        verify(dealRepository, Mockito.times(0)).save(any(DealEntity.class));
        verify(productDealRepository, Mockito.times(0))
                .save(any(ProductDealEntity.class));
        verify(dealRepository, Mockito.times(0)).findByName(anyString());
        verify(productRepository, Mockito.times(1)).findByName(productEntity.getName());
        verify(dealRepository, Mockito.times(0))
                .updateTotalCost(anyLong(), BigDecimal.valueOf(anyLong()));

    }

    /**
     * Тест получения списка всех продуктов всех сделок
     */
    @Test
    void findAll() {
        List<ProductDealEntity> listEntity = new ArrayList<>();
        listEntity.add(firstProductDealEntity);
        listEntity.add(secondProductDealEntity);
        given(productDealRepository.findAll()).willReturn(listEntity);
        List<ProductDealDto> allProductDealList = productDealService.findAll();
        assertEquals(2, allProductDealList.size());
        assertEquals(firstProductDealDto, allProductDealList.get(0));
        assertEquals(secondProductDealDto, allProductDealList.get(1));
        verify(productDealRepository, Mockito.times(1)).findAll();
    }

    /**
     * Тест получения списка всех продуктов конкретной сделки
     */

    @Test
    void findByDealName() {
        List<ProductDealEntity> listEntity = new ArrayList<>();
        listEntity.add(firstProductDealEntity);
        Optional<DealEntity> dealEntityOptional = Optional.of(firstDealEntity);
        given(dealRepository.findByName(firstDealEntity.getName())).willReturn(dealEntityOptional);
        given(productDealRepository.findByDealEntity(dealEntityOptional.get())).willReturn(listEntity);
        List<ProductDealDto> allProductDealList = productDealService.findByDealName(firstProductDealDto.getDealName());
        assertEquals(1, allProductDealList.size());
        assertEquals(firstProductDealDto, allProductDealList.get(0));
        verify(productDealRepository, Mockito.times(1)).findByDealEntity(dealEntityOptional.get());
        verify(dealRepository, Mockito.times(1)).findByName(firstDealEntity.getName());

    }

    /**
     * Тест обновления записи о продукте сделки
     */
    @Test
    void update() {
        ProductDealDto updateDto = ProductDealDto.builder().productId(secondProductDealDto.getProductId())
                .dealId(secondProductDealDto.getDealId()).unit("кг").build();
        ProductDealEntity updateEntity = ProductDealEntity.builder().id(secondProductDealEntity.getId())
                .unit(updateDto.getUnit()).quantity(secondProductDealEntity.getQuantity())
                .cost(new BigDecimal("300.00")).build();
        Optional<DealEntity> dealEntityOptional = Optional.of(secondDealEntity);
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        Optional<ProductDealEntity> productDealEntityOptional = Optional.of(secondProductDealEntity);
        given(dealRepository.findById(secondDealEntity.getId())).willReturn(dealEntityOptional);
        given(productRepository.findById(productEntity.getId())).willReturn(productEntityOptional);
        given(productDealRepository.findById(secondProductDealEntity.getId())).willReturn(productDealEntityOptional);
        assertDoesNotThrow(() -> productDealService.update(updateDto));
        verify(productDealRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(updateEntity)));
        verify(productDealRepository, Mockito.times(1)).findById(secondProductDealEntity.getId());
        verify(dealRepository, Mockito.times(1)).findById(secondDealEntity.getId());
        verify(dealRepository, Mockito.times(1))
                .updateTotalCost(updateDto.getDealId(), updateEntity.getCost());
        verify(productRepository, Mockito.times(1)).findById(productEntity.getId());

    }

    /**
     * Тест неудачной попытки обновления несуществующей записи о продукте сделки
     */
    @Test
    void updateNonExistentRecord() {
        Optional<DealEntity> dealEntityOptional = Optional.of(secondDealEntity);
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        Optional<ProductDealEntity> productDealEntityOptional = Optional.empty();
        given(dealRepository.findById(secondDealEntity.getId())).willReturn(dealEntityOptional);
        given(productRepository.findById(productEntity.getId())).willReturn(productEntityOptional);
        given(productDealRepository.findById(secondProductDealEntity.getId())).willReturn(productDealEntityOptional);
        Assertions.assertThrows(Exception.class, () -> productDealService.update(secondProductDealDto));
        verify(productDealRepository, Mockito.times(0)).save(any(ProductDealEntity.class));
        verify(productDealRepository, Mockito.times(1)).findById(secondProductDealEntity.getId());
        verify(dealRepository, Mockito.times(1)).findById(secondDealEntity.getId());
        verify(productRepository, Mockito.times(1)).findById(productEntity.getId());
    }

    /**
     * Тест неудачной попытки обновления записи о продукте сделки с несуществующим внешним ключом продукта
     */
    @Test
    void updateWrongForeignKey() {
        Optional<DealEntity> dealEntityOptional = Optional.of(secondDealEntity);
        Optional<ProductEntity> productEntityOptional = Optional.empty();
        given(dealRepository.findById(secondDealEntity.getId())).willReturn(dealEntityOptional);
        given(productRepository.findById(productEntity.getId())).willReturn(productEntityOptional);
        Assertions.assertThrows(Exception.class, () -> productDealService.update(secondProductDealDto));
        verify(productDealRepository, Mockito.times(0)).save(any(ProductDealEntity.class));
        verify(dealRepository, Mockito.times(1)).findById(secondDealEntity.getId());
        verify(productRepository, Mockito.times(1)).findById(productEntity.getId());
    }

    /**
     * Тест неудачной попытки обновления записи о продукте сделки с неправильной единицей измерения
     */
    @Test
    void updateUnableConvert() {
        ProductDealDto updateDto = ProductDealDto.builder().productId(secondProductDealDto.getProductId())
                .dealId(secondProductDealDto.getDealId()).quantity(BigDecimal.valueOf(3)).build();
        Optional<DealEntity> dealEntityOptional = Optional.of(secondDealEntity);
        Optional<ProductEntity> productEntityOptional = Optional.of(productEntity);
        Optional<ProductDealEntity> productDealEntityOptional = Optional.of(secondProductDealEntity);
        given(dealRepository.findById(secondDealEntity.getId())).willReturn(dealEntityOptional);
        given(productRepository.findById(productEntity.getId())).willReturn(productEntityOptional);
        given(productDealRepository.findById(secondProductDealEntity.getId())).willReturn(productDealEntityOptional);
        Assertions.assertThrows(Exception.class, () -> productDealService.update(updateDto));
        verify(productDealRepository, Mockito.times(0)).save(any(ProductDealEntity.class));
        verify(productDealRepository, Mockito.times(1)).findById(secondProductDealEntity.getId());
        verify(dealRepository, Mockito.times(1)).findById(secondDealEntity.getId());
        verify(productRepository, Mockito.times(1)).findById(productEntity.getId());
    }
}
