package org.example.service;

import org.example.dto.DealDto;
import org.example.entity.ClientEntity;
import org.example.entity.DealEntity;
import org.example.entity.UserEntity;
import org.example.repository.ClientRepository;
import org.example.repository.DealRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис сделок
 */
@Service
public class DealService implements ServiceInterface<DealDto> {
    /**
     * Репозиторий для записей о сделках
     */
    private final DealRepository dealRepository;
    /**
     * Репозиторий для записей о компании
     */
    private final ClientRepository clientRepository;
    /**
     * Репозиторий для записей о пользователе
     */
    private final UserRepository userRepository;

    @Autowired
    public DealService(DealRepository dealRepository, ClientRepository clientRepository, UserRepository userRepository) {
        this.dealRepository = dealRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    /**
     * Добавление новой сделки
     *
     * @param dealDto DTO-объект сделки
     * @throws Exception ошибка внешнего ключа
     */
    @Override
    public void add(DealDto dealDto) throws Exception {
        DealEntity dealEntity = DealEntity.builder().name(dealDto.getName())
                .stage(dealDto.getStage()).type(dealDto.getType())
                .startDate(dealDto.getStartDate()).endDate(dealDto.getEndDate()).totalCost(BigDecimal.ZERO).build();
        if (dealDto.getClientId() != null) {
            Optional<ClientEntity> clientEntity = clientRepository.findById(dealDto.getClientId());
            if (clientEntity.isEmpty()) {
                throw new Exception("Не найдена запись о клиенте");
            } else {
                dealEntity.setClient(clientEntity.get());
            }
        } else {
            throw new Exception("Выберите клиента, участвующего в сделке");
        }
        if (dealDto.getUserUsername() != null) {
            Optional<UserEntity> userEntity = userRepository.findByUsername(dealDto.getUserUsername());
            if (userEntity.isEmpty()) {
                throw new Exception("Не найдена запись о сотруднике");
            } else {
                dealEntity.setUser(userEntity.get());
            }
        } else {
            throw new Exception("Выберите сотрудника, участвующего в сделке");
        }
        dealRepository.save(dealEntity);
    }

    /**
     * Обновление записи о сделке
     *
     * @param dealDto DTO с заполненными полями для обновления
     * @throws Exception не найдена запись по id или ошибка внешнего ключа
     */
    @Override
    public void update(DealDto dealDto) throws Exception {
        Optional<DealEntity> dealEntityOptional = dealRepository.findById(dealDto.getId());
        if (dealEntityOptional.isEmpty()) {
            throw new Exception("Нет записи для обновления");
        }
        DealEntity dealEntity = dealEntityOptional.get();
        if (dealDto.getName() != null && !dealDto.getName().trim().isEmpty()) {
            dealEntity.setName(dealDto.getName());
        }
        if (dealDto.getType() != null && !dealDto.getType().trim().isEmpty()) {
            dealEntity.setType(dealDto.getType());
        }
        if (dealDto.getStage() != null && !dealDto.getStage().trim().isEmpty()) {
            dealEntity.setStage(dealDto.getStage());
        }
        if (dealDto.getStartDate() != null) {
            dealEntity.setStartDate(dealDto.getStartDate());
        }
        if (dealDto.getEndDate() != null) {
            dealEntity.setEndDate(dealDto.getEndDate());
        }
        if (dealDto.getUserUsername() != null && !dealDto.getUserUsername().trim().isEmpty()) {
            Optional<UserEntity> userEntityOptional = userRepository.findByUsername(dealDto.getUserUsername());
            if (userEntityOptional.isEmpty()) {
                throw new Exception("Не найдена запись о сотруднике");
            }
            dealEntity.setUser(userEntityOptional.get());
        }
        if (dealDto.getClientId() != null) {
            Optional<ClientEntity> clientEntityOptional = clientRepository.findById(dealDto.getClientId());
            if (clientEntityOptional.isEmpty()) {
                throw new Exception("Не найдена запись о клиенте");
            }
            dealEntity.setClient(clientEntityOptional.get());
        }
        dealRepository.save(dealEntity);
    }

    /**
     * Получение списка всех сделок
     *
     * @return список всех сделок
     */
    @Override
    public List<DealDto> findAll() {
        List<DealDto> dealDtos = new ArrayList<>();
        List<DealEntity> entityList = dealRepository.findAll();
        for (DealEntity entity : entityList) {
            DealDto dealDto = DealDto.builder().id(entity.getId()).name(entity.getName())
                    .type(entity.getType()).stage(entity.getStage())
                    .totalCost(entity.getTotalCost()).startDate(entity.getStartDate())
                    .endDate(entity.getEndDate()).build();
            dealDto.setClientId(entity.getClient().getId());
            String fio = entity.getClient().getLastName() + (" ")
                    + entity.getClient().getName().substring(0, 1) + (". ");
            if (entity.getClient().getMiddleName() != null) {
                fio = fio + entity.getClient().getMiddleName().substring(0, 1) + (".");
            }
            dealDto.setClientFio(fio);
            dealDto.setUserUsername(entity.getUser().getUsername());
            dealDtos.add(dealDto);
        }
        return dealDtos;
    }

    /**
     * Получение списка сделок по логину сотрудника
     *
     * @param username логин сотрудника
     * @return список сделок сотрудника
     */
    public List<DealDto> findByUsername(String username) {
        List<DealDto> dealDtos = new ArrayList<>();
        List<DealEntity> entityList = dealRepository.findByUserUsername(username);
        for (DealEntity entity : entityList) {
            DealDto dealDto = DealDto.builder().id(entity.getId()).name(entity.getName())
                    .type(entity.getType()).stage(entity.getStage())
                    .totalCost(entity.getTotalCost()).startDate(entity.getStartDate())
                    .endDate(entity.getEndDate()).build();
            dealDto.setClientId(entity.getClient().getId());
            String fio = entity.getClient().getLastName() + (" ")
                    + entity.getClient().getName().substring(0, 1) + (". ");
            if (entity.getClient().getMiddleName() != null) {
                fio = fio + entity.getClient().getMiddleName().substring(0, 1) + (".");
            }
            dealDto.setClientFio(fio);
            dealDto.setUserUsername(entity.getUser().getUsername());
            dealDtos.add(dealDto);
        }
        return dealDtos;
    }
}
