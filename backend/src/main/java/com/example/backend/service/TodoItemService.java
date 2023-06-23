package com.example.backend.service;

import com.example.backend.DTO.TodoItemDTOI;
import com.example.backend.DTO.TodoItemDTOO;
import com.example.backend.DTO.TodoItemIsDoneDTOI;
import com.example.backend.enums.ActionCounterName;
import com.example.backend.model.TodoItem;
import com.example.backend.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    @Qualifier("entityToDTOConversionService")
    private ConversionService conversionService;

    @Autowired
    private ActionCounterService actionCounterService;

    public List<TodoItemDTOO> getAllTodoItems() {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_ALL);

        List<TodoItem> todoItems = todoItemRepository.findAll();

        return conversionService.todoItemListToDTOO(todoItems);
    }

    public List<TodoItemDTOO> getAllTodoItemsWithFiltering(String name, Boolean isDone, Boolean isAscending) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_WITH_FILTER);

        // Perform standard search
        List<TodoItem> allTodoItems = todoItemRepository.findAll();
        List<TodoItem> todoItemsToRet = new ArrayList<>(allTodoItems);

        // Search for name: case-insensitive, contains (optionally)
        if (name != null) {
            todoItemsToRet = todoItemsToRet.stream()
                            .filter(it -> it.getName().toLowerCase().contains(name.toLowerCase()))
                            .collect(Collectors.toList());
        }

        // Search for isDone (optionally)
        if (isDone != null) {
            Integer isDoneInt = isDone ? 1 : 0;
            todoItemsToRet = todoItemsToRet.stream()
                    .filter(it -> it.getIsDone().equals(isDoneInt))
                    .collect(Collectors.toList());
        }

        // Order by createdOn date
        if (isAscending != null) {
            Collections.sort(
                    todoItemsToRet,
                    (i1, i2) -> {
                        Date d1 = i1.getCreatedOn();
                        Date d2 = i2.getCreatedOn();

                        return isAscending ? d1.compareTo(d2) : d2.compareTo(d1);
                    }
            );
        }

        // Transform filtered items to DTOO and return them
        return conversionService.todoItemListToDTOO(todoItemsToRet);
    }

    public Optional<TodoItemDTOO> getTodoItemById(Long id) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.READ_ONE);

        Optional<TodoItem> o_entity = todoItemRepository.findById(id);
        TodoItemDTOO dto;

        if (o_entity.isPresent()) {
            dto = conversionService.todoItemToDTOO(o_entity.get());

            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public Optional<TodoItemDTOO> createTodoItem(TodoItemDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.CREATE);

        Optional<TodoItem> o_entity = todoItemRepository.findByName(dtoi.getName());
        if (!o_entity.isPresent()) {
            TodoItem entity = conversionService.todoItemDTOIToEntity(dtoi);
            TodoItemDTOO result;

            entity = todoItemRepository.save(entity);
            result = conversionService.todoItemToDTOO(entity);

            return Optional.of(result);
        }
        return Optional.empty();
    }

    public boolean updateTodoItem(Long id, TodoItemDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.UPDATE);

        Optional<TodoItem> o_entity = todoItemRepository.findById(id);

        if (o_entity.isPresent()) {
            TodoItem entity = conversionService.todoItemDTOIToEntity(dtoi);
            entity.setId(o_entity.get().getId());
            entity.setCreatedOn(o_entity.get().getCreatedOn());
            todoItemRepository.save(entity);

            return true;
        }
        return false;
    }

    public boolean updateStatusOfTodoItem(Long id, TodoItemIsDoneDTOI dtoi) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.UPDATE);

        Optional<TodoItem> o_entity = todoItemRepository.findById(id);

        if (o_entity.isPresent()) {
            TodoItem entity = o_entity.get();
            entity.setIsDone(dtoi.getDone() ? 1 : 0);
            todoItemRepository.save(entity);

            return true;
        }
        return false;
    }

    public boolean deleteTodoItem(Long id) {
        // Increase ActionCounter
        actionCounterService.increaseActionCounter(ActionCounterName.DELETE);

        Optional<TodoItem> o_entity = todoItemRepository.findById(id);
        if (o_entity.isPresent()) {
            TodoItem entity = o_entity.get();
            todoItemRepository.delete(entity);

            return true;
        }

        return false;
    }
}
