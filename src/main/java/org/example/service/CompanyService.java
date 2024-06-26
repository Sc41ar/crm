package org.example.service;

import org.example.dto.CompanyDto;
import org.example.entity.CompanyEntity;
import org.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис компании
 */
@Service
public class CompanyService {
    /**
     * Репозиторий для записей о компании
     */
    @Autowired
    private CompanyRepository repository;

    /**
     * Добавление новой компании
     *
     * @param companyDto DTO-объект компании
     */

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
    public List<CompanyDto> findAll() {
        List<CompanyEntity> entityList = repository.findAll();
        List<CompanyDto> companyDtos = new ArrayList<>();
        for (CompanyEntity entity : entityList) {
            companyDtos.add(CompanyDto.builder().name(entity.getName())
                    .phoneNumber(entity.getPhoneNumber()).email(entity.getEmail())
                    .address(entity.getAddress()).description(entity.getDescription()).build());
        }
        return companyDtos;
    }
}
