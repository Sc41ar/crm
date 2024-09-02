package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dto.DealDto;
import org.example.entity.ClientEntity;
import org.example.entity.DealEntity;
import org.example.entity.UserEntity;
import org.example.repository.ClientRepository;
import org.example.repository.DealRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис сделок
 */
@Service
public class DealService {
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

    public DealDto add(DealDto dealDto) throws Exception {
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
        dealEntity = dealRepository.save(dealEntity);
        return DealDto.builder()
                .id(dealEntity.getId())
                .name(dealEntity.getName())
                .stage(dealEntity.getStage())
                .startDate(dealEntity.getStartDate())
                .endDate(dealEntity.getEndDate())
                .totalCost(dealEntity.getTotalCost())
                .build();
    }

    /**
     * Обновление записи о сделке
     *
     * @param dealDto DTO с заполненными полями для обновления
     * @throws Exception не найдена запись по id или ошибка внешнего ключа
     */

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
     * Получение списка сделок по почте сотрудника
     *
     * @param email электронная почта сотрудника
     * @return список сделок сотрудника
     */
    public List<DealDto> findByEmail(String email) {
        List<DealDto> dealDtos = new ArrayList<>();
        List<DealEntity> entityList = dealRepository.findByUserEmail(email);
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
    public List<DealDto> findByUsername(String username, Pageable pageable) {
        List<DealDto> dealDtos = new ArrayList<>();
        Page<DealEntity> entityPage = dealRepository.findByUserUsername(username, pageable);
        for (DealEntity entity : entityPage) {
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
     * Получение списка сделок для конкретного месяца и года
     *
     * @param monthYear строка месяц-год
     * @return список сделок
     */
    public List<DealDto> findByMonthYear(String monthYear) {
        List<DealDto> dealDtos = new ArrayList<>();
        Integer month = Integer.valueOf(monthYear.split("-")[0]);
        Integer year = Integer.valueOf(monthYear.split("-")[1]);
        List<DealEntity> entityList = dealRepository.findByMonth(month, year);
        for (DealEntity entity : entityList) {
            DealDto dealDto = DealDto.builder().name(entity.getName())
                    .type(entity.getType()).stage(entity.getStage())
                    .totalCost(entity.getTotalCost()).startDate(entity.getStartDate())
                    .endDate(entity.getEndDate()).build();
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
     * Формирование файла excel с информацией о сделках за конкретный месяц
     *
     * @param monthYear строка месяц-год, для которого формируется отчет
     * @return рабочая книга excel
     */
    public Workbook createXLSX(String monthYear) {
        List<DealDto> dealDtos = findByMonthYear(monthYear);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Deal");
        for (int i = 0; i != 8; i++) {
            sheet.setColumnWidth(i, 5000);
        }
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name Deal");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue("Stage");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(2);
        headerCell.setCellValue("Total cost");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(3);
        headerCell.setCellValue("Type");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(4);
        headerCell.setCellValue("Start date");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(5);
        headerCell.setCellValue("End date");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(6);
        headerCell.setCellValue("Client");
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(7);
        headerCell.setCellValue("User");
        headerCell.setCellStyle(headerStyle);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER); // выравнивание по центру
        style.setVerticalAlignment(VerticalAlignment.CENTER); // вертикальное выравнивание по центру
        for (int i = 0; i != dealDtos.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(dealDtos.get(i).getName());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(dealDtos.get(i).getStage());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(dealDtos.get(i).getTotalCost().toString());
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue(dealDtos.get(i).getType());
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue(dealDtos.get(i).getStartDate().toString());
            cell.setCellStyle(style);
            cell = row.createCell(5);
            if (dealDtos.get(i).getEndDate() != null) cell.setCellValue(dealDtos.get(i).getEndDate().toString());
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue(dealDtos.get(i).getClientFio());
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue(dealDtos.get(i).getUserUsername());
            cell.setCellStyle(style);
        }
        return workbook;
    }
}
