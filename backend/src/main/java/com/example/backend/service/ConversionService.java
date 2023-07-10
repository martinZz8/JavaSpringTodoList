package com.example.backend.service;

import com.example.backend.DTOO.ActionCounterDTOO;
import com.example.backend.DTOI.TodoItemDTOI;
import com.example.backend.DTOO.UserDTOO;
import com.example.backend.model.ActionCounter;
import com.example.backend.model.Role;
import com.example.backend.model.TodoItem;
import com.example.backend.DTOO.TodoItemDTOO;
import com.example.backend.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("entityToDTOConversionService")
public class ConversionService {
    // -- Entity to DTOO --
    // TodoItem
    public TodoItemDTOO todoItemToDTOO(TodoItem entity) {
        TodoItemDTOO dto = new TodoItemDTOO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDone(
                entity.getIsDone() != null ?
                    entity.getIsDone() == 1
                :
                    false
        );
        dto.setCreatedOn(entity.getCreatedOn());

        UserDTOO uDTO = userToDTOO(entity.getUser());
        dto.setUser(uDTO);

        return dto;
    }

    public List<TodoItemDTOO> todoItemListToDTOO(List<TodoItem> entity) {
        List<TodoItemDTOO> result = new ArrayList<>();

        for (TodoItem ti: entity) {
            TodoItemDTOO dto;
            dto = todoItemToDTOO(ti);
            result.add(dto);
        }

        return result;
    }

    // ActionCounter
    public ActionCounterDTOO actionCounterToDTOO(ActionCounter entity) {
        ActionCounterDTOO dto = new ActionCounterDTOO();
        dto.setId(entity.getId());
        dto.setActionName(entity.getActionName());
        dto.setCounter(entity.getCounter());
        dto.setUpdateOn(entity.getUpdateOn());

        return dto;
    }

    public List<ActionCounterDTOO> actionCounterListToDTOO(List<ActionCounter> entity) {
        List<ActionCounterDTOO> result = new ArrayList<>();

        for (ActionCounter ac: entity) {
            ActionCounterDTOO dto;
            dto = actionCounterToDTOO(ac);
            result.add(dto);
        }

        return result;
    }

    // User
    public UserDTOO userToDTOO(User entity) {
        UserDTOO dto = new UserDTOO();

        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());

        List<String> roles = entity.getRoles().stream()
                .map(it -> it.getName().name())
                .collect(Collectors.toList());
        dto.setRoles(roles);

        return dto;
    }


    // -- DTOO to entity --
    // TodoItem
    public TodoItem todoItemDTOOToEntity(TodoItemDTOO dto) {
        TodoItem ti = new TodoItem();
        ti.setId(dto.getId());
        ti.setName(dto.getName());
        ti.setDescription(dto.getDescription());
        ti.setIsDone(dto.getDone() ? 1 : 0);
        ti.setCreatedOn(dto.getCreatedOn());

        User u = userDTOOToEntity(dto.getUser());
        ti.setUser(u);

        return ti;
    }

    // ActionCounter
    public ActionCounter actionCounterDTOOToEntity(ActionCounterDTOO dto) {
        ActionCounter ac = new ActionCounter();
        ac.setId(dto.getId());
        ac.setActionName(dto.getActionName());
        ac.setCounter(dto.getCounter());
        ac.setUpdateOn(dto.getUpdateOn());

        return ac;
    }

    // User
    public User userDTOOToEntity(UserDTOO dto) {
        User u = new User();
        u.setId(dto.getId());
        u.setEmail(dto.getEmail());
        u.setUsername(dto.getUsername());

        return u;
    }

    // -- DTOI to entity --
    // TodoItem
    public TodoItem todoItemDTOIToEntity(TodoItemDTOI dtoi) {
        TodoItem ti = new TodoItem();
        ti.setName(dtoi.getName());
        ti.setDescription(dtoi.getDescription());
        if (dtoi.getIsDone() != null) {
            ti.setIsDone(dtoi.getIsDone() ? 1 : 0);
        }

        return ti;
    }
}
