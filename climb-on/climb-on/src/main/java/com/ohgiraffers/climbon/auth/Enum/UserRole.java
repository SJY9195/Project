package com.ohgiraffers.climbon.auth.Enum;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    BUSINESS("BUSINESS");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                '}';
    }
}
