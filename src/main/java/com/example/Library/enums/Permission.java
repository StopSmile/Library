package com.example.Library.enums;

public enum Permission {

    PERMISSION_ADMIN("user:admin"),
    PERMISSION_CLIENT("user:client"),
    PERMISSION_GUEST("user:guest");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
