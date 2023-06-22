package com.example.backend.repository;

import com.example.backend.model.ActionCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionCounterRepository extends JpaRepository<ActionCounter, Long> {

}
