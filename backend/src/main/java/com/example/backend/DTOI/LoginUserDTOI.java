package com.example.backend.DTOI;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class LoginUserDTOI {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    LoginUserDTOI(){}

    public LoginUserDTOI(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

