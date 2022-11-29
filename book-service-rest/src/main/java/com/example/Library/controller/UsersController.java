package com.example.Library.controller;


import com.example.Library.dto.UserDTO;
import com.example.Library.exceptions.IncorrectGenderException;
import com.example.Library.exceptions.IncorrectRoleException;
import com.example.Library.exceptions.IncorrectUserStatusException;
import com.example.Library.exceptions.UserAlreadyExists;
import com.example.Library.repositories.UserRepository;
import com.example.Library.services.UserService;
import com.example.Library.services.impl.LoginAttemptService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UsersController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final Set<String> GENDER_SET = new HashSet<>(Arrays.asList("MALE", "FEMALE"));
    private final Set<String> ROLE_SET = new HashSet<>(Arrays.asList("ADMIN", "CLIENT", "GUEST"));
    private final Set<String> USER_STATUS_SET = new HashSet<>(Arrays.asList("ACTIVE", "BANNED"));
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    public UsersController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('users:read')")
    @GetMapping()
    @Operation(summary = "Get all blocked users")
    public ArrayList<String> getAllBlockedUsers() {
        return loginAttemptService.getAllBlockedUsers();
    }

    @PreAuthorize("hasAuthority('users:create')")
    @PostMapping()
    @Operation(summary = "Add a user to the library using parameters.")
    public UserDTO addUser(@RequestParam(value = "firstName") String firstName,
                           @RequestParam(value = "lastName") String lastName,
                           @RequestParam(value = "age") int age,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "gender") String gender,
                           @RequestParam(value = "role") String role,
                           @RequestParam(value = "userStatus") String userStatus) {
        log.info("Executing method POST /api/v1/users with params : firstName = " + firstName + ",lastName = " + lastName + ",age = " + age + ",email = " + email
                + ",password = " + password + ",gender = " + gender + ",role = " + role + ",userStatus = " + userStatus);
        boolean checkUser = userRepository.existsByEmail(email);
        if (checkUser) {
            log.error("User with email " + email + " already exists");
            throw new UserAlreadyExists(email);
        }
        UserDTO userDTO = new UserDTO();
        if (GENDER_SET.contains(gender)) {
            userDTO.setGender(gender);
        } else {
            throw new IncorrectGenderException(gender);
        }
        if (ROLE_SET.contains(role)) {
            userDTO.setRole(role);
        } else {
            throw new IncorrectRoleException(role);
        }
        if (USER_STATUS_SET.contains(userStatus)) {
            userDTO.setUserStatus(userStatus);
        } else {
            throw new IncorrectUserStatusException(userStatus);
        }
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setAge(age);
        userDTO.setEmail(email);
        userDTO.setPassword(passwordEncoder.encode(password));
        return userService.addUser(userDTO);
    }
}
