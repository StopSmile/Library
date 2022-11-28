package com.example.Library.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    ADMIN(Set.of(Permission.BOOKS_CREATE,Permission.BOOKS_READ,Permission.BOOKS_UPDATE,Permission.BOOKS_DELETE,Permission.USERS_CREATE,Permission.USERS_READ)),
    CLIENT(Set.of(Permission.BOOKS_READ,Permission.BOOKS_UPDATE)),
    GUEST(Set.of(Permission.BOOKS_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
