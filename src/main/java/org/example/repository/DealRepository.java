package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {
    /**
     * Поиск записей по логину сотрудника
     *
     * @param username логин сотрудника
     * @return список из сущностей сделок
     */
    List<DealEntity> findByUserUsername(String username);

    /**
     * Поиск записи по названию сделки
     *
     * @param name название сделки
     * @return сущность сделки
     */
    Optional<DealEntity> findByName(String name);

    /**
     * Обновление данных о полной стоимости сделки
     *
     * @param id        идентификатор сделки
     * @param totalCost новая стоимость сделки
     */
    @Query("UPDATE DealEntity SET totalCost = :costDeal WHERE id = :idDeal")
    @Modifying
    @Transactional
    void updateTotalCost(@Param("idDeal") Long id, @Param("costDeal") BigDecimal totalCost);

    List<DealEntity> findByUserEmail(String email);

    /**
     * Поиск записей по месяцу и году сделки
     *
     * @param month месяц
     * @param year  год
     * @return список из сущностей сделок
     */

    @Query("SELECT d FROM DealEntity d WHERE MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year")
    List<DealEntity> findByMonth(@Param("month") Integer month, @Param("year") Integer year);
}
