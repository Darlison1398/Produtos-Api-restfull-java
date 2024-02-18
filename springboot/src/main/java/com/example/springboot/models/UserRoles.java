package com.example.springboot.models;

public enum UserRoles {

    ADMIN("admin"),
    USER("user");

    private String role;

    // construtor dessa classe
    UserRoles(String role){
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
}
