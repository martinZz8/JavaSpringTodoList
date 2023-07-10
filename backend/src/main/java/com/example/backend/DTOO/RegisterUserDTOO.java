package com.example.backend.DTOO;

public class RegisterUserDTOO {
    private String message;

    public RegisterUserDTOO() {}

    public RegisterUserDTOO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
