package com.example.backend.DTOI;

import jakarta.validation.constraints.NotEmpty;

public class TodoItemDTOI {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private Boolean isDone;

    public TodoItemDTOI(){}

    public TodoItemDTOI(String name, String description, Boolean isDone) {
        this.name = name;
        this.description = description;
        this.isDone = isDone;
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

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
}
