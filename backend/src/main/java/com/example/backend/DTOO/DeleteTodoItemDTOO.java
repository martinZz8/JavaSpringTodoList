package com.example.backend.DTOO;

public class DeleteTodoItemDTOO {
    private Boolean isPresent;
    private Boolean isAuthorized;

    public DeleteTodoItemDTOO() {}

    public DeleteTodoItemDTOO(Boolean isPresent, Boolean isAuthorized) {
        this.isPresent = isPresent;
        this.isAuthorized = isAuthorized;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(Boolean authorized) {
        isAuthorized = authorized;
    }
}
