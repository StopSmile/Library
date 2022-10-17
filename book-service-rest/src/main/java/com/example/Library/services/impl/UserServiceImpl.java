package com.example.Library.services.impl;

import com.example.Library.dto.UserDTO;
import com.example.Library.model.User;
import com.example.Library.repositories.UserRepository;
import com.example.Library.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> findByEmail(String email) {
        log.info("Get user by email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        log.info("User with {} email is found", email);
        return Optional.ofNullable(userMapper.mapUserToUserDTO(user));
    }
}
