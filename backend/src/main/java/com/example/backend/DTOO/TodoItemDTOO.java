package com.example.backend.DTOO;

import java.util.Date;

public class TodoItemDTOO {
    private Long id;
    private String name;
    private String description;
    private Date createdOn;
    private Boolean isDone;

    public TodoItemDTOO(){}
    public TodoItemDTOO(Long id, String name, String description, Date createdOn, Boolean isDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdOn = createdOn;
        this.isDone = isDone;
    }

    public TodoItemDTOO(TodoItemDTOO todoItem) {
        this.id = todoItem.getId();
        this.name = todoItem.getName();
        this.description = todoItem.getDescription();
        this.createdOn = todoItem.getCreatedOn();
        this.isDone = todoItem.getDone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
