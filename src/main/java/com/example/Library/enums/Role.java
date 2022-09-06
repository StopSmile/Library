package com.example.Library.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    GUEST(Set.of(Permission.PERMISSION_GUEST)),
    ADMIN(Set.of(Permission.PERMISSION_ADMIN,Permission.PERMISSION_CLIENT,Permission.PERMISSION_GUEST)),
    CLIENT(Set.of(Permission.PERMISSION_CLIENT,Permission.PERMISSION_GUEST));

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
