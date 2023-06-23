package com.example.backend.repository;

import com.example.backend.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    Optional<TodoItem> findByName(String name);
}
