package com.example.backend.DTO;

import jakarta.validation.constraints.NotEmpty;

public class TodoItemIsDoneDTOI {
    @NotEmpty
    private Boolean isDone;

    public TodoItemIsDoneDTOI() {}

    public TodoItemIsDoneDTOI(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }
}
