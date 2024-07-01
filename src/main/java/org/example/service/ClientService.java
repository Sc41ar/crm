package org.example.service;

import org.example.dto.ClientDto;
import org.example.entity.ClientEntity;
import org.example.entity.CompanyEntity;
import org.example.repository.ClientRepository;
import org.example.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис клиента
 */
@Service
public class ClientService implements ServiceInterface<ClientDto> {
    /**
     * Репозиторий для записей о клиентах
     */
    private final ClientRepository clientRepository;
    /**
     * Репозиторий для записей о компании
     */
    private final CompanyRepository companyRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, CompanyRepository companyRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Добавление нового клиента
     *
     * @param clientDto DTO-объект клиента
     * @throws Exception ошибка внешнего ключа
     */
    @Override
    public void add(ClientDto clientDto) throws Exception {
        ClientEntity clientEntity = ClientEntity.builder().lastName(clientDto.getLastName())
                .name(clientDto.getName()).middleName(clientDto.getMiddleName())
                .email(clientDto.getEmail()).phoneNumber(clientDto.getPhoneNumber()).address(clientDto.getAddress())
                .description(clientDto.getDescription()).build();
        if (clientDto.getIdCompany() != null) {
            Optional<CompanyEntity> companyEntity = companyRepository.findById(clientDto.getIdCompany());
            if (companyEntity.isEmpty()) {
                throw new Exception("Не найдена запись о компании");
            } else {
                clientEntity.setCompany(companyEntity.get());
            }
        }
        clientRepository.save(clientEntity);
    }

    /**
     * Обновление записи о клиенте
     *
     * @param clientDto DTO с заполненными полями для обновления
     * @throws Exception ошибка внешнего ключа или не найдена запись по id
     */
    @Override
    public void update(ClientDto clientDto) throws Exception {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findById(clientDto.getId());
        if (clientEntityOptional.isEmpty()) {
            throw new Exception("Нет записи для обновления");
        }
        ClientEntity clientEntity = clientEntityOptional.get();
        if (clientDto.getLastName() != null && !clientDto.getLastName().trim().isEmpty()) {
            clientEntity.setLastName(clientDto.getLastName());
        }
        if (clientDto.getName() != null && !clientDto.getName().trim().isEmpty()) {
            clientEntity.setName(clientDto.getName());
        }
        if (clientDto.getMiddleName() != null && !clientDto.getMiddleName().trim().isEmpty()) {
            clientEntity.setMiddleName(clientDto.getMiddleName());
        }
        if (clientDto.getEmail() != null && !clientDto.getEmail().trim().isEmpty()) {
            clientEntity.setEmail(clientDto.getEmail());
        }
        if (clientDto.getPhoneNumber() != null && !clientDto.getPhoneNumber().trim().isEmpty()) {
            clientEntity.setPhoneNumber(clientDto.getPhoneNumber());
        }
        if (clientDto.getAddress() != null && !clientDto.getAddress().trim().isEmpty()) {
            clientEntity.setAddress(clientDto.getAddress());
        }
        if (clientDto.getDescription() != null && !clientDto.getDescription().trim().isEmpty()) {
            clientEntity.setName(clientDto.getDescription());
        }
        if (clientDto.getIdCompany() != null) {
            if (companyRepository.findById(clientDto.getIdCompany()).isEmpty()) {
                throw new Exception("Не найдена запись о компании");
            }
            clientEntity.setCompany(companyRepository.findById(clientDto.getIdCompany()).get());
        }
        clientRepository.save(clientEntity);
    }

    /**
     * Получение списка всех клиентов
     *
     * @return список из DTO-объектов клиентов
     */
    @Override
    public List<ClientDto> findAll() {
        List<ClientEntity> entityList = clientRepository.findAll();
        List<ClientDto> clientDtos = new ArrayList<>();
        for (ClientEntity entity : entityList) {
            ClientDto clientDto = ClientDto.builder().id(entity.getId()).lastName(entity.getLastName())
                    .name(entity.getName()).middleName(entity.getMiddleName())
                    .email(entity.getEmail()).phoneNumber(entity.getPhoneNumber())
                    .address(entity.getAddress()).description(entity.getDescription())
                    .build();
            if (entity.getCompany() != null) {
                clientDto.setIdCompany(entity.getCompany().getId());
                clientDto.setCompanyName(entity.getCompany().getName());
            }
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }
}
