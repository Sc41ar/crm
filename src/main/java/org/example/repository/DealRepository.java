package org.example.repository;

import org.example.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<DealEntity, Long> {
    /**
     * Поиск записей по логину сотрудника
     *
     * @param username логин сотрудника
     * @return список из сущностей сделок
     */
    List<DealEntity> findByUserUsername(String username);
}
