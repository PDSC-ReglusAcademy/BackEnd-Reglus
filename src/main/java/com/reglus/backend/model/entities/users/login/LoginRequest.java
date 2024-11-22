package com.reglus.backend.model.entities.users.login;

public class LoginRequest {
    private String email;
    private String password;

    // Getters e setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
