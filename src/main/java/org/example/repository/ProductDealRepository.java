package org.example.repository;

import org.example.entity.DealEntity;
import org.example.entity.ProductDealEntity;
import org.example.entity.ProductDealPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDealRepository extends JpaRepository<ProductDealEntity, Long> {
    /**
     * Поиск записи по идентификатору
     *
     * @param pk составной первичный ключ
     * @return сущность продукта сделки
     */
    Optional<ProductDealEntity> findById(ProductDealPK pk);

    /**
     * Поиск записей по конкретной сделке
     *
     * @param deal сущность сделки
     * @return список сущностей продуктов этой сделки
     */
    @Query("SELECT pd FROM ProductDealEntity pd WHERE pd.id.deal = :dealEntity")
    List<ProductDealEntity> findByDealEntity(@Param("dealEntity") DealEntity deal);
}
