package com.example.Library.security;

import com.example.Library.dto.UserDTO;
import com.example.Library.enums.Role;
import com.example.Library.enums.UserStatus;
import com.example.Library.services.impl.UserMapper;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;

    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static UserDetails fromUser(UserDTO userDTO){
        UserMapper userMapper = new UserMapper();

        return new org.springframework.security.core.userdetails.User(
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getUserStatus().equals("ACTIVE"),
                userDTO.getUserStatus().equals("ACTIVE"),
                userDTO.getUserStatus().equals("ACTIVE"),
                userDTO.getUserStatus().equals("ACTIVE"),
                userMapper.getRoleFromUserDto(userDTO).getAuthorities()
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
