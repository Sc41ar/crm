package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.dto.ClientDto;
import org.example.entity.ClientEntity;
import org.example.entity.CompanyEntity;
import org.example.repository.ClientRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервисов клиентов
 */
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    /**
     * Entity-объект компании
     */
    private final CompanyEntity companyEntity = CompanyEntity.builder().id(2L)
            .name(RandomString.make(10)).build();
    /**
     * DTO-объекты клиента
     */
    private final ClientDto firstClientDto = ClientDto.builder().id(1L).name(RandomString.make(10))
            .lastName(RandomString.make(10)).address(RandomString.make(20))
            .email(RandomString.make(10)).phoneNumber(RandomString.make(12)).build();
    private final ClientDto secondClientDto = ClientDto.builder().name(RandomString.make(10))
            .lastName(RandomString.make(10)).address(RandomString.make(20))
            .email(RandomString.make(10)).phoneNumber(RandomString.make(12))
            .idCompany(companyEntity.getId()).companyName(companyEntity.getName()).build();
    /**
     * Entity-объекты клиента
     */
    private final ClientEntity secondClientEntity = ClientEntity.builder().name(secondClientDto.getName())
            .lastName(secondClientDto.getLastName()).address(secondClientDto.getAddress())
            .email(secondClientDto.getEmail()).phoneNumber(secondClientDto.getPhoneNumber())
            .company(companyEntity).build();
    private final ClientEntity firstClientEntity = ClientEntity.builder().id(firstClientDto.getId())
            .name(firstClientDto.getName()).lastName(firstClientDto.getLastName()).address(firstClientDto.getAddress())
            .email(firstClientDto.getEmail()).phoneNumber(firstClientDto.getPhoneNumber()).build();
    /**
     * Мок объект репозитория клиента
     */
    @Mock
    private ClientRepository clientRepository;
    /**
     * Мок объект репозитория компании
     */
    @Mock
    private CompanyRepository companyRepository;
    /**
     * Сервис клиента
     */
    @InjectMocks
    private ClientService clientService;

    /**
     * Тест добавления клиента
     */
    @Test
    void add() {
        Optional<CompanyEntity> entityOptional = Optional.of(companyEntity);
        given(companyRepository.findById(secondClientDto.getIdCompany())).willReturn(entityOptional);
        assertDoesNotThrow(() -> clientService.add(secondClientDto));
        verify(clientRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(secondClientEntity)));
        verify(companyRepository, Mockito.times(1)).findById(secondClientDto.getIdCompany());
    }

    /**
     * Тест неудачной попытки добавления клиента с несуществующим внешним ключом компании
     */
    @Test
    void addWrongForeignKey() {
        Optional<CompanyEntity> emptyOptional = Optional.empty();
        given(companyRepository.findById(companyEntity.getId())).willReturn(emptyOptional);
        Assertions.assertThrows(Exception.class, () -> clientService.add(secondClientDto));
        verify(clientRepository, Mockito.times(0))
                .save(any(ClientEntity.class));
        verify(companyRepository, Mockito.times(1)).findById(companyEntity.getId());

    }

    /**
     * Тест получения списка всех клиентов
     */
    @Test
    void findAll() {
        List<ClientEntity> listEntity = new ArrayList<>();
        listEntity.add(firstClientEntity);
        listEntity.add(secondClientEntity);
        given(clientRepository.findAll()).willReturn(listEntity);
        List<ClientDto> allClientList = clientService.findAll();
        assertEquals(2, allClientList.size());
        assertEquals(firstClientDto, allClientList.get(0));
        assertEquals(secondClientDto, allClientList.get(1));
        verify(clientRepository, Mockito.times(1)).findAll();

    }

    /**
     * Тест обновления записи о клиенте
     */
    @Test
    void update() {
        ClientDto updateDto = ClientDto.builder().id(firstClientDto.getId())
                .email(RandomString.make(10)).idCompany(companyEntity.getId()).build();
        ClientEntity updateEntity = ClientEntity.builder().id(firstClientDto.getId())
                .email(updateDto.getEmail()).address(firstClientDto.getAddress())
                .name(firstClientDto.getName()).lastName(firstClientDto.getLastName())
                .phoneNumber(firstClientDto.getPhoneNumber()).company(companyEntity).build();
        Optional<ClientEntity> clientEntityOptional = Optional.of(firstClientEntity);
        Optional<CompanyEntity> companyEntityOptional = Optional.of(companyEntity);
        given(clientRepository.findById(firstClientDto.getId())).willReturn(clientEntityOptional);
        given(companyRepository.findById(companyEntity.getId())).willReturn(companyEntityOptional);
        assertDoesNotThrow(() -> clientService.update(updateDto));
        verify(clientRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(updateEntity)));
        verify(clientRepository, Mockito.times(1)).findById(firstClientDto.getId());
        verify(companyRepository, Mockito.times(1)).findById(companyEntity.getId());
    }

    /**
     * Тест неудачной попытки обновления несуществующей записи о клиенте
     */
    @Test
    void updateNonExistentRecord() {
        Optional<ClientEntity> emptyOptional = Optional.empty();
        given(clientRepository.findById(firstClientDto.getId())).willReturn(emptyOptional);
        Assertions.assertThrows(Exception.class, () -> clientService.update(firstClientDto));
        verify(clientRepository, Mockito.times(0)).save(any(ClientEntity.class));
        verify(clientRepository, Mockito.times(1)).findById(firstClientDto.getId());
        verify(companyRepository, Mockito.times(0)).findById(anyLong());
    }

    /**
     * Тест неудачной попытки обновления записи о клиенте с несуществующим внешним ключом компании
     */
    @Test
    void updateWrongForeignKey() {
        ClientDto updateDto = ClientDto.builder().id(firstClientDto.getId()).idCompany(companyEntity.getId()).build();
        Optional<ClientEntity> clientEntityOptional = Optional.of(firstClientEntity);
        Optional<CompanyEntity> companyEntityOptional = Optional.empty();
        given(clientRepository.findById(firstClientDto.getId())).willReturn(clientEntityOptional);
        given(companyRepository.findById(companyEntity.getId())).willReturn(companyEntityOptional);
        Assertions.assertThrows(Exception.class, () -> clientService.update(updateDto));
        verify(clientRepository, Mockito.times(0)).save(any(ClientEntity.class));
        verify(clientRepository, Mockito.times(1)).findById(firstClientDto.getId());
        verify(companyRepository, Mockito.times(1)).findById(companyEntity.getId());
    }
}
