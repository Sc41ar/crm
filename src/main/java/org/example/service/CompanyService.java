package org.example.service;

import org.example.dto.CompanyDto;
import org.example.entity.CompanyEntity;
import org.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис компании
 */
@Service
public class CompanyService implements ServiceInterface<CompanyDto> {
    /**
     * Репозиторий для записей о компании
     */
    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    /**
     * Добавление новой компании
     *
     * @param companyDto DTO-объект компании
     */
    @Override
    public void add(CompanyDto companyDto) {
        CompanyEntity companyEntity = CompanyEntity.builder().name(companyDto.getName())
                .phoneNumber(companyDto.getPhoneNumber()).email(companyDto.getEmail())
                .address(companyDto.getAddress()).description(companyDto.getDescription()).build();
        repository.save(companyEntity);
    }

    /**
     * Получение списка всех компаний
     *
     * @return список из DTO-объектов компаний
     */
    @Override
    public List<CompanyDto> findAll() {
        List<CompanyEntity> entityList = repository.findAll();
        List<CompanyDto> companyDtos = new ArrayList<>();
        for (CompanyEntity entity : entityList) {
            companyDtos.add(CompanyDto.builder().id(entity.getId()).name(entity.getName())
                    .phoneNumber(entity.getPhoneNumber()).email(entity.getEmail())
                    .address(entity.getAddress()).description(entity.getDescription()).build());
        }
        return companyDtos;
    }

    /**
     * Обновление записи о компании
     *
     * @param companyDto DTO с заполненными полями для обновления
     * @throws Exception не найдена запись по id
     */
    @Override
    public void update(CompanyDto companyDto) throws Exception {
        Optional<CompanyEntity> companyEntityOptional = repository.findById(companyDto.getId());
        if (companyEntityOptional.isEmpty()) {
            throw new Exception("Нет записи для обновления");
        }
        CompanyEntity companyEntity = companyEntityOptional.get();
        if (companyDto.getName() != null && !companyDto.getName().trim().isEmpty()) {
            companyEntity.setName(companyDto.getName());
        }
        if (companyDto.getPhoneNumber() != null && !companyDto.getPhoneNumber().trim().isEmpty()) {
            companyEntity.setPhoneNumber(companyDto.getPhoneNumber());
        }
        if (companyDto.getEmail() != null && !companyDto.getEmail().trim().isEmpty()) {
            companyEntity.setEmail(companyDto.getEmail());
        }
        if (companyDto.getAddress() != null && !companyDto.getAddress().trim().isEmpty()) {
            companyEntity.setAddress(companyDto.getAddress());
        }
        if (companyDto.getDescription() != null && !companyDto.getDescription().trim().isEmpty()) {
            companyEntity.setDescription(companyDto.getDescription());
        }
        repository.save(companyEntity);
    }
}
