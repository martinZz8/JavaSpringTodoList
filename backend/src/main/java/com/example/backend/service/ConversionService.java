package com.example.backend.service;

import com.example.backend.DTO.ActionCounterDTOO;
import com.example.backend.DTO.TodoItemDTOI;
import com.example.backend.model.ActionCounter;
import com.example.backend.model.TodoItem;
import com.example.backend.DTO.TodoItemDTOO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("entityToDTOConversionService")
public class ConversionService {
    // -- Entity to DTOO --
    // TodoItem
    public TodoItemDTOO todoItemToDTOO(TodoItem entity) {
        TodoItemDTOO dto = new TodoItemDTOO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDone(entity.getIsDone() == 1);
        dto.setCreatedOn(entity.getCreatedOn());

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

    // -- DTOO to entity --
    // TodoItem
    public TodoItem todoItemDTOOToEntity(TodoItemDTOO dto) {
        TodoItem ti = new TodoItem();
        ti.setId(dto.getId());
        ti.setName(dto.getName());
        ti.setDescription(dto.getDescription());
        ti.setIsDone(dto.getDone() ? 1 : 0);
        ti.setCreatedOn(dto.getCreatedOn());

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

    // -- DTOI to entity --
    // TodoItem
    public TodoItem todoItemDTOIToEntity(TodoItemDTOI dtoi) {
        TodoItem ti = new TodoItem();
        ti.setName(dtoi.getName());
        ti.setDescription(dtoi.getDescription());
        ti.setIsDone(dtoi.getIsDone() ? 1 : 0);

        return ti;
    }

}
