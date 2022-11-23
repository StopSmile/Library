package com.example.Library.services;

import com.example.Library.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findByEmail(String email);
    UserDTO addUser(UserDTO userDTO);

}
