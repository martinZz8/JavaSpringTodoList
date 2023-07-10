package com.example.backend.DTOO;

import java.util.List;

public class UserDTOO {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public UserDTOO() {}

    public UserDTOO(Long id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public UserDTOO(UserDTOO user) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.roles = user.roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
