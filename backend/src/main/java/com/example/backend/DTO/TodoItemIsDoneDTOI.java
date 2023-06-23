package com.example.backend.DTO;

public class TodoItemIsDoneDTOI {
    private Boolean isDone;

    public TodoItemIsDoneDTOI(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
