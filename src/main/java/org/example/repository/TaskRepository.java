package org.example.repository;

import org.example.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByName(String name);

    List<TaskEntity> findByAuthorUsername(String username);

    List<TaskEntity> findByAuthorEmail(String email);
}