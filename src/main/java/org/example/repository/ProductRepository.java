package org.example.repository;

import org.example.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    /**
     * Поиск продукта по названию
     *
     * @param name название продукта
     * @return сущность продукта
     */
    Optional<ProductEntity> findByName(String name);
}
