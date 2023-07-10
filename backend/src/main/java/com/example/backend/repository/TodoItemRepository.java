package com.example.backend.repository;

import com.example.backend.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    Optional<TodoItem> findByName(String name);

    @Query("SELECT ti FROM todo_item ti WHERE ti.user.id = ?1")
    List<TodoItem> findAllByUserId(Long userId);

    @Query("SELECT ti FROM todo_item ti WHERE ti.user.username = ?1")
    List<TodoItem> findAllByUserName(String username);
}
