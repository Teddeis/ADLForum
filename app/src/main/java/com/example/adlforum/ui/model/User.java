package com.example.adlforum.ui.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String password_hash;
    private String role;
    public boolean is_active;
    private String avatar_url;

    // Геттеры и сеттеры
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return password_hash;
    }
    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    // Остальные геттеры/сеттеры...
}

