package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий для записей о пользователях
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Поиск пользователя по логину
     * @param username логин
     * @return сущность пользователя
     */

    Optional<UserEntity> findByUsername(String username);

    /**
     * Поиск пользователя по электронной почте
     * @param email электронная почта
     * @return сущность пользователя
     */
    Optional<UserEntity> findByEmail(String email);
}
