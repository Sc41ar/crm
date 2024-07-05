package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.dto.CompanyDto;
import org.example.entity.CompanyEntity;
import org.example.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса компаний
 */
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    /**
     * DTO-объекты компании
     */
    private final CompanyDto firstCompanyDto = CompanyDto.builder().id(1L)
            .email(RandomString.make(10)).address(RandomString.make(30)).name(RandomString.make(10))
            .phoneNumber(RandomString.make(12)).build();
    private final CompanyDto secondCompanyDto = CompanyDto.builder().email(RandomString.make(10))
            .address(RandomString.make(30)).name(RandomString.make(10))
            .phoneNumber(RandomString.make(12)).build();
    /**
     * Entity-объекты компании
     */
    private final CompanyEntity firstCompanyEntity = CompanyEntity.builder().id(firstCompanyDto.getId())
            .email(firstCompanyDto.getEmail()).address(firstCompanyDto.getAddress())
            .name(firstCompanyDto.getName()).phoneNumber(firstCompanyDto.getPhoneNumber()).build();
    private final CompanyEntity secondCompanyEntity = CompanyEntity.builder().email(secondCompanyDto.getEmail())
            .address(secondCompanyDto.getAddress()).name(secondCompanyDto.getName())
            .phoneNumber(secondCompanyDto.getPhoneNumber()).build();
    /**
     * Мок объект репозитория компании
     */
    @Mock
    private CompanyRepository companyRepository;
    /**
     * Сервис компании
     */
    @InjectMocks
    private CompanyService companyService;

    /**
     * Тест получения списка всех компаний
     */
    @Test
    void findAll() {
        List<CompanyEntity> listEntity = new ArrayList<>();
        listEntity.add(firstCompanyEntity);
        listEntity.add(secondCompanyEntity);
        given(companyRepository.findAll()).willReturn(listEntity);
        List<CompanyDto> allCompanyList = companyService.findAll();
        assertEquals(2, allCompanyList.size());
        assertEquals(firstCompanyDto, allCompanyList.get(0));
        assertEquals(secondCompanyDto, allCompanyList.get(1));
        verify(companyRepository, Mockito.times(1)).findAll();
    }

    /**
     * Тест добавления компании
     */
    @Test
    void add() {
        companyService.add(secondCompanyDto);
        verify(companyRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(secondCompanyEntity)));
    }

    /**
     * Тест обновления записи о компании
     */
    @Test
    void update() {
        CompanyDto updateDto = CompanyDto.builder().id(firstCompanyDto.getId())
                .email(RandomString.make(10)).build();
        CompanyEntity updateEntity = CompanyEntity.builder().id(firstCompanyDto.getId())
                .email(updateDto.getEmail()).address(firstCompanyDto.getAddress())
                .name(firstCompanyDto.getName()).phoneNumber(firstCompanyDto.getPhoneNumber()).build();
        Optional<CompanyEntity> entityOptional = Optional.of(firstCompanyEntity);
        given(companyRepository.findById(firstCompanyDto.getId())).willReturn(entityOptional);
        assertDoesNotThrow(() -> companyService.update(updateDto));
        verify(companyRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(updateEntity)));
        verify(companyRepository, Mockito.times(1)).findById(firstCompanyDto.getId());

    }

    /**
     * Тест неудачной попытки обновления несуществующей записи
     */
    @Test
    void updateNonExistentRecord() {
        Optional<CompanyEntity> emptyOptional = Optional.empty();
        given(companyRepository.findById(firstCompanyDto.getId())).willReturn(emptyOptional);
        Assertions.assertThrows(Exception.class, () -> companyService.update(firstCompanyDto));
        verify(companyRepository, Mockito.times(0)).save(any(CompanyEntity.class));
        verify(companyRepository, Mockito.times(1)).findById(firstCompanyDto.getId());
    }

}
