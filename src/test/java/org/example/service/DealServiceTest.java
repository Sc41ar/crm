package org.example.service;

import net.bytebuddy.utility.RandomString;
import org.example.dto.DealDto;
import org.example.entity.ClientEntity;
import org.example.entity.DealEntity;
import org.example.entity.UserEntity;
import org.example.repository.ClientRepository;
import org.example.repository.DealRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса сделок
 */
@ExtendWith(MockitoExtension.class)
public class DealServiceTest {
    /**
     * Entity-объект клиента для внешнего ключа
     */
    private final ClientEntity clientEntity = ClientEntity.builder().id(1L).lastName(RandomString.make(10))
            .name(RandomString.make(10)).build();
    /**
     * Entity-объекты пользователя для внешнего ключа
     */
    private final UserEntity firstUserEntity = UserEntity.builder().id(2L).username(RandomString.make(10)).build();
    private final UserEntity secondUserEntity = UserEntity.builder().id(3L).username(RandomString.make(10)).build();
    /**
     * Первый DTO-объект сделки
     */
    private final DealDto firstDealDto = DealDto.builder().id(3L).stage(RandomString.make(8))
            .type(RandomString.make(8)).name(RandomString.make(10))
            .startDate(new Date(2024, 1, 1)).clientId(clientEntity.getId())
            .userUsername(firstUserEntity.getUsername()).build();
    /**
     * Первый Entity-объект сделки
     */
    private final DealEntity firstDealEntity = DealEntity.builder().id(firstDealDto.getId())
            .stage(firstDealDto.getStage()).type(firstDealDto.getType()).name(firstDealDto.getName())
            .startDate(firstDealDto.getStartDate()).user(firstUserEntity).client(clientEntity).build();
    /**
     * Второй DTO-объект сделки
     */
    private final DealDto secondDealDto = DealDto.builder().stage(RandomString.make(8))
            .type(RandomString.make(8)).name(RandomString.make(10)).totalCost(BigDecimal.ZERO)
            .startDate(new Date(2024, 2, 2)).clientId(clientEntity.getId())
            .userUsername(secondUserEntity.getUsername()).build();
    /**
     * Второй Entity-объект
     */
    private final DealEntity secondDealEntity = DealEntity.builder().stage(secondDealDto.getStage())
            .type(secondDealDto.getType()).name(secondDealDto.getName()).totalCost(BigDecimal.ZERO)
            .startDate(secondDealDto.getStartDate()).user(secondUserEntity).client(clientEntity).build();
    /**
     * Мок объект репозитория сделок
     */
    @Mock
    private DealRepository dealRepository;
    /**
     * Мок объект репозитория клиента
     */
    @Mock
    private ClientRepository clientRepository;
    /**
     * Мок объект репозитория пользователей
     */
    @Mock
    private UserRepository userRepository;
    /**
     * Сервис сделок
     */
    @InjectMocks
    private DealService dealService;

    /**
     * Тест получения списка всех сделок
     */
    @Test
    void findAll() {
        List<DealEntity> listEntity = new ArrayList<>();
        listEntity.add(firstDealEntity);
        listEntity.add(secondDealEntity);
        given(dealRepository.findAll()).willReturn(listEntity);
        String fio = clientEntity.getLastName() + (" ") + clientEntity.getName().substring(0, 1) + (". ");
        firstDealDto.setClientFio(fio);
        secondDealDto.setClientFio(fio);
        List<DealDto> allDealList = dealService.findAll();
        assertEquals(2, allDealList.size());
        assertEquals(firstDealDto, allDealList.get(0));
        assertEquals(secondDealDto, allDealList.get(1));
        verify(dealRepository, Mockito.times(1)).findAll();
        firstDealDto.setClientFio(null);
        secondDealDto.setClientFio(null);
    }

    /**
     * Тест добавления сделки
     */
    @Test
    void add() {
        Optional<ClientEntity> clientEntityOptional = Optional.of(clientEntity);
        Optional<UserEntity> userEntityOptional = Optional.of(secondUserEntity);
        given(clientRepository.findById(secondDealDto.getClientId())).willReturn(clientEntityOptional);
        given(userRepository.findByUsername(secondDealDto.getUserUsername())).willReturn(userEntityOptional);
        assertDoesNotThrow(() -> dealService.add(secondDealDto));
        verify(dealRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(secondDealEntity)));
        verify(clientRepository, Mockito.times(1)).findById(secondDealDto.getClientId());
        verify(userRepository, Mockito.times(1)).findByUsername(secondDealDto.getUserUsername());
    }

    /**
     * Тест неудачной попытки добавления сделки с несуществующим внешним ключом пользователя
     */
    @Test
    void addWrongForeignKey() {
        Optional<ClientEntity> clientEntityOptional = Optional.of(clientEntity);
        Optional<UserEntity> userEntityOptional = Optional.empty();
        given(clientRepository.findById(secondDealDto.getClientId())).willReturn(clientEntityOptional);
        given(userRepository.findByUsername(secondDealDto.getUserUsername())).willReturn(userEntityOptional);
        Assertions.assertThrows(Exception.class, () -> dealService.add(secondDealDto));
        verify(dealRepository, Mockito.times(0)).save(any(DealEntity.class));
        verify(clientRepository, Mockito.times(1)).findById(secondDealDto.getClientId());
        verify(userRepository, Mockito.times(1)).findByUsername(secondDealDto.getUserUsername());
    }

    /**
     * Тест  неудачной попытки добавления сделки с нулевым внешним ключом пользователя
     */
    @Test
    void addNullForeignKey() {
        Optional<ClientEntity> clientEntityOptional = Optional.of(clientEntity);
        given(clientRepository.findById(secondDealDto.getClientId())).willReturn(clientEntityOptional);
        secondDealDto.setUserUsername(null);
        Assertions.assertThrows(Exception.class, () -> dealService.add(secondDealDto));
        verify(dealRepository, Mockito.times(0)).save(any(DealEntity.class));
        verify(clientRepository, Mockito.times(1)).findById(secondDealDto.getClientId());
        verify(userRepository, Mockito.times(0)).findByUsername(anyString());
        secondDealDto.setUserUsername(secondUserEntity.getUsername());

    }

    /**
     * Тест обновления записи о сделке
     */
    @Test
    void update() {
        DealDto updateDto = DealDto.builder().id(firstDealDto.getId()).stage(RandomString.make(14))
                .type(RandomString.make(10)).name(RandomString.make(12))
                .userUsername(secondUserEntity.getUsername()).build();
        DealEntity updateEntity = DealEntity.builder().id(firstDealDto.getId()).stage(updateDto.getStage())
                .type(updateDto.getType()).name(updateDto.getName())
                .startDate(firstDealDto.getStartDate()).user(secondUserEntity).client(clientEntity).build();
        Optional<UserEntity> userEntityOptional = Optional.of(secondUserEntity);
        given(userRepository.findByUsername(updateDto.getUserUsername())).willReturn(userEntityOptional);
        Optional<DealEntity> dealEntityOptional = Optional.of(firstDealEntity);
        given(dealRepository.findById(firstDealDto.getId())).willReturn(dealEntityOptional);
        assertDoesNotThrow(() -> dealService.update(updateDto));
        verify(dealRepository, Mockito.times(1))
                .findById(firstDealDto.getId());
        verify(dealRepository, Mockito.times(1))
                .save(Mockito.argThat(arg -> arg.equals(updateEntity)));
        verify(userRepository, Mockito.times(1)).findByUsername(secondDealDto.getUserUsername());

    }

    /**
     * Тест неудачной попытки обновления несуществующей записи о сделке
     */
    @Test
    void updateNonExistentRecord() {
        Optional<DealEntity> emptyOptional = Optional.empty();
        given(dealRepository.findById(firstDealDto.getId())).willReturn(emptyOptional);
        Assertions.assertThrows(Exception.class, () -> dealService.update(firstDealDto));
        verify(dealRepository, Mockito.times(0)).save(any(DealEntity.class));
        verify(dealRepository, Mockito.times(1)).findById(firstDealDto.getId());
        verify(clientRepository, Mockito.times(0)).findById(anyLong());
        verify(userRepository, Mockito.times(0)).findByUsername(anyString());
    }

    /**
     * Тест неудачной попытки обновления записи о сделке с несуществующим внешним ключом пользователя
     */
    @Test
    void updateWrongForeignKey() {
        DealDto updateDto = DealDto.builder().id(firstDealDto.getId())
                .userUsername(secondUserEntity.getUsername()).build();
        Optional<UserEntity> userEntityOptional = Optional.empty();
        Optional<DealEntity> dealEntityOptional = Optional.of(firstDealEntity);
        given(dealRepository.findById(firstDealDto.getId())).willReturn(dealEntityOptional);
        given(userRepository.findByUsername(secondUserEntity.getUsername())).willReturn(userEntityOptional);
        Assertions.assertThrows(Exception.class, () -> dealService.update(updateDto));
        verify(dealRepository, Mockito.times(0)).save(any(DealEntity.class));
        verify(dealRepository, Mockito.times(1)).findById(firstDealDto.getId());
        verify(clientRepository, Mockito.times(0)).findById(anyLong());
        verify(userRepository, Mockito.times(1)).findByUsername(secondUserEntity.getUsername());

    }

    /**
     * Тест получения списка всех сделок конкретного пользователя
     */
    @Test
    void findByUsername() {
        List<DealEntity> listEntity = new ArrayList<>();
        listEntity.add(firstDealEntity);
        given(dealRepository.findByUserUsername(firstUserEntity.getUsername())).willReturn(listEntity);
        String fio = firstDealEntity.getClient().getLastName() + (" ") + firstDealEntity.getClient().getName().substring(0, 1) + (". ");
        firstDealDto.setClientFio(fio);
        List<DealDto> allDealList = dealService.findByUsername(firstDealDto.getUserUsername());
        assertEquals(1, allDealList.size());
        assertEquals(firstDealDto, allDealList.get(0));
        verify(dealRepository, Mockito.times(1)).findByUserUsername(firstDealDto.getUserUsername());
        firstDealDto.setClientFio(null);
    }
}
