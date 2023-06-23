package com.example.backend.repository;

import com.example.backend.model.ActionCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionCounterRepository extends JpaRepository<ActionCounter, Long> {
    Optional<ActionCounter> findByActionName(String name);
}
