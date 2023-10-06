package com.example.findsnils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnilsRepository extends JpaRepository<SnilsEntity, Long> {
    Optional<SnilsEntity> findBySnils(String snils);
}
