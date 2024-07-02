package org.example.service;

import org.example.dto.ProductDealDto;
import org.example.entity.DealEntity;
import org.example.entity.ProductDealEntity;
import org.example.entity.ProductDealPK;
import org.example.entity.ProductEntity;
import org.example.repository.DealRepository;
import org.example.repository.ProductDealRepository;
import org.example.repository.ProductRepository;
import org.example.service.converter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис продуктов сделок
 */
@Service
public class ProductDealService implements ServiceInterface<ProductDealDto> {
    /**
     * Репозиторий для записей о продуктах сделок
     */
    private final ProductDealRepository productDealRepository;
    /**
     * Репозиторий для записей о сделках
     */
    private final DealRepository dealRepository;
    /**
     * Репозиторий для записей о продуктах
     */
    private final ProductRepository productRepository;
    /**
     * Лист конвертеров
     */
    private ArrayList<UnitConverter> converterArrayList;

    @Autowired
    public ProductDealService(ProductDealRepository productDealRepository,
                              DealRepository dealRepository, ProductRepository productRepository) {
        this.productDealRepository = productDealRepository;
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
        converterArrayList = new ArrayList<>();
        converterArrayList.add(new LengthConverter());
        converterArrayList.add(new TimeConverter());
        converterArrayList.add(new VolumeConverter());
        converterArrayList.add(new MassConverter());
    }

    /**
     * Добавление нового продукта к сделке
     *
     * @param productDealDto DTO-объект продукта сделки
     * @throws Exception ошибка внешнего ключа
     */
    @Override
    public void add(ProductDealDto productDealDto) throws Exception {
        Optional<ProductEntity> productEntityOptional = productRepository.findByName(productDealDto.getProductName());
        if (productEntityOptional.isEmpty()) {
            throw new Exception("Не найден продукт");
        }
        ProductEntity productEntity = productEntityOptional.get();
        BigDecimal cost = priceRecalculation(productEntity.getUnit(),
                productDealDto.getUnit(), productEntity.getUnitPrice(), productDealDto.getQuantity());
        Optional<DealEntity> dealEntityOptional = dealRepository.findByName(productDealDto.getDealName());
        if (dealEntityOptional.isEmpty()) {
            throw new Exception("Не найдена сделка");
        }
        ProductDealPK pk = new ProductDealPK(dealEntityOptional.get(), productEntity);
        ProductDealEntity productDealEntity = ProductDealEntity.builder().id(pk).cost(cost)
                .quantity(productDealDto.getQuantity()).unit(productDealDto.getUnit()).build();
        productDealRepository.save(productDealEntity);
        BigDecimal totalCost = dealEntityOptional.get().getTotalCost().add(cost);
        dealRepository.updateTotalCost(dealEntityOptional.get().getId(), totalCost);
    }

    /**
     * Получение всех продуктов сделки
     *
     * @return список всех продуктов во всех сделках
     */
    @Override
    public List<ProductDealDto> findAll() {
        List<ProductDealDto> productDealDtos = new ArrayList<>();
        List<ProductDealEntity> entityList = productDealRepository.findAll();
        for (ProductDealEntity entity : entityList) {
            ProductDealDto productDealDto = ProductDealDto.builder().cost(entity.getCost())
                    .unit(entity.getUnit()).quantity(entity.getQuantity()).build();
            productDealDto.setDealId(entity.getId().getDeal().getId());
            productDealDto.setDealName(entity.getId().getDeal().getName());
            productDealDto.setProductId(entity.getId().getProduct().getId());
            productDealDto.setProductName(entity.getId().getProduct().getName());
            productDealDtos.add(productDealDto);
        }
        return productDealDtos;
    }

    /**
     * Получение списка продуктов для конкретной сделки
     *
     * @param dealName название сделки
     * @return список продуктов сделки
     */
    public List<ProductDealDto> findByDealName(String dealName) {
        List<ProductDealDto> productDealDtos = new ArrayList<>();
        Optional<DealEntity> dealEntityOptional = dealRepository.findByName(dealName);
        if (dealEntityOptional.isEmpty()) {
            return productDealDtos;
        }
        List<ProductDealEntity> entityList = productDealRepository.findByDealEntity(dealEntityOptional.get());
        for (ProductDealEntity entity : entityList) {
            ProductDealDto productDealDto = ProductDealDto.builder().cost(entity.getCost())
                    .unit(entity.getUnit()).quantity(entity.getQuantity()).build();
            productDealDto.setDealId(entity.getId().getDeal().getId());
            productDealDto.setDealName(entity.getId().getDeal().getName());
            productDealDto.setProductId(entity.getId().getProduct().getId());
            productDealDto.setProductName(entity.getId().getProduct().getName());
            productDealDtos.add(productDealDto);
        }
        return productDealDtos;
    }

    /**
     * Обновление записи о продукте сделки
     *
     * @param productDealDto DTO с заполненными полями для обновления
     * @throws Exception не найдена запись по id или ошибка внешнего ключа
     */
    @Override
    public void update(ProductDealDto productDealDto) throws Exception {
        Optional<DealEntity> dealEntityOptional = dealRepository.findById(productDealDto.getDealId());
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productDealDto.getProductId());
        if (dealEntityOptional.isEmpty()) {
            throw new Exception("Сделки не существует");
        }
        if (productEntityOptional.isEmpty()) {
            throw new Exception("Продукта не существует");
        }
        ProductEntity productEntity = productEntityOptional.get();
        ProductDealPK pk = new ProductDealPK(dealEntityOptional.get(), productEntity);
        Optional<ProductDealEntity> productDealEntityOptional = productDealRepository.findById(pk);
        if (productDealEntityOptional.isEmpty()) {
            throw new Exception("Нет записи для обновления");
        }
        ProductDealEntity productDealEntity = productDealEntityOptional.get();
        if (productDealDto.getUnit() != null && !productDealDto.getUnit().trim().isEmpty()) {
            productDealEntity.setUnit(productDealDto.getUnit());
        }
        if (productDealDto.getQuantity() != null) {
            productDealEntity.setQuantity(productDealDto.getQuantity());
        }
        BigDecimal cost = priceRecalculation(productEntity.getUnit(), productDealEntity.getUnit(),
                productEntity.getUnitPrice(), productDealEntity.getQuantity());
        if (productDealEntity.getCost().compareTo(cost) != 0) {
            BigDecimal totalCost = dealEntityOptional.get().getTotalCost().subtract(productDealEntity.getCost());
            productDealEntity.setCost(cost);
            dealRepository.updateTotalCost(dealEntityOptional.get().getId(), totalCost.add(cost));
        }
        productDealRepository.save(productDealEntity);
    }

    /**
     * Пересчет стоимости продукта с учетом конвертации
     *
     * @param fromUnit единица измерения, из которой переводим
     * @param toUnit   единица измерения в которую переводим
     * @param price    цена для fromUnit
     * @param quantity количество
     * @return стоимость продукта
     * @throws Exception ошибка при конвертации (единицы измерения разных типов)
     */
    public BigDecimal priceRecalculation(String fromUnit, String toUnit,
                                         BigDecimal price, BigDecimal quantity) throws Exception {
        if (fromUnit == toUnit) {
            return price.multiply(quantity).setScale(2, RoundingMode.HALF_EVEN);
        }
        BigDecimal priceUnit = BigDecimal.ZERO;
        for (UnitConverter converter : converterArrayList) {
            if (converter.isOneTypeUnits(fromUnit, toUnit)) {
                priceUnit = converter.convert(fromUnit, toUnit, price);
            }
        }
        if (priceUnit.compareTo(BigDecimal.ZERO) == 0) {
            throw new Exception("Невозможно конвертировать");
        }
        BigDecimal cost = priceUnit.multiply(quantity).setScale(2, RoundingMode.HALF_EVEN);
        return cost;
    }
}
