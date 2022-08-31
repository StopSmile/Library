package com.example.Library.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Role {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @Transient
    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority ADMIN = new SimpleGrantedAuthority("ADMIN");
        SimpleGrantedAuthority CLIENT = new SimpleGrantedAuthority("CLIENT");
        SimpleGrantedAuthority GUEST = new SimpleGrantedAuthority("GUEST");
        authorities.add(ADMIN);
        authorities.add(CLIENT);
        authorities.add(GUEST);
        return authorities;
    }
}
