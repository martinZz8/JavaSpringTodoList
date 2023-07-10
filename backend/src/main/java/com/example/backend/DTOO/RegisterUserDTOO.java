package com.example.backend.DTOO;

public class RegisterUserDTOO {
    private Boolean isError;
    private String message;

    public RegisterUserDTOO() {}

    public RegisterUserDTOO(Boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
