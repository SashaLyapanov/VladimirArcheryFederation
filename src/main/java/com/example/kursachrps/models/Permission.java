package com.example.kursachrps.models;

public enum Permission {
    SPORTSMEN_READ("sportsmen:read"),
    SPORTSMAN_UPDATE("sportsman:update"),
    SPORTSMAN_DELETE("sportsman:delete"),
    SPORTSMAN_WRITE("sportsman:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() { return permission; }


}
