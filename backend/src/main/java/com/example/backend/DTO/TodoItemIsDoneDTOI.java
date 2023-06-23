package com.example.backend.DTO;

public class TodoItemIsDoneDTOI {
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
