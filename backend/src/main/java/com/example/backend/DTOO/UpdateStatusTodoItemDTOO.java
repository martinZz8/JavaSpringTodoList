package com.example.backend.DTOO;

public class UpdateStatusTodoItemDTOO {
    private Boolean isPresent;
    private Boolean isAuthorized;

    public UpdateStatusTodoItemDTOO() {}

    public UpdateStatusTodoItemDTOO(Boolean isPresent, Boolean isAuthorized) {
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
