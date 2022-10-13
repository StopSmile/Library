package com.example.Library.services;

import com.example.Library.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
}
