package org.example.service;

import org.example.dto.CompanyDto;
import org.example.entity.CompanyEntity;
import org.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
