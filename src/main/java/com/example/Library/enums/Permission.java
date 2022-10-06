package com.example.Library.enums;

public enum Permission {
    BOOKS_CREATE("books:create"),
    BOOKS_READ("books:read"),
    BOOKS_UPDATE("books:update"),
    BOOKS_DELETE("books:delete");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
