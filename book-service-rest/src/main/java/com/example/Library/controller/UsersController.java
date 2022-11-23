package com.example.Library.controller;


import com.example.Library.dto.UserDTO;
import com.example.Library.exceptions.IncorrectGenderException;
import com.example.Library.exceptions.IncorrectRoleException;
import com.example.Library.exceptions.IncorrectUserStatusException;
import com.example.Library.exceptions.UserAlreadyExists;
import com.example.Library.repositories.UserRepository;
import com.example.Library.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UsersController {

    private final UserService userService;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('books:create')")
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
        if (!gender.equals("MALE") && !gender.equals("FEMALE")) {
            log.error("You set incorrect user gender : " + gender + ". At this moment we support 2 genders: MALE and FEMALE. Please choose one of these.");
            throw new IncorrectGenderException(gender);
        }
        if (!role.equals("ADMIN") && !role.equals("CLIENT") && !role.equals("GUEST")) {
            log.error("You set incorrect user role : " + role + ". At this moment we support 3 roles: ADMIN, CLIENT and GUEST. Please choose one of these.");
            throw new IncorrectRoleException(role);
        }
        if (!userStatus.equals("ACTIVE") && !userStatus.equals("BANNED")) {
            log.error("You set incorrect user status : " + userStatus + ". At this moment we support 2 statuses: ACTIVE and BANNED. Please choose one of these.");
            throw new IncorrectUserStatusException(userStatus);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setAge(age);
        userDTO.setEmail(email);
        userDTO.setPassword(passwordEncoder.encode(password));
        if (gender.equals("MALE")) {
            userDTO.setGender("MALE");
        }
        if (gender.equals("FEMALE")) {
            userDTO.setGender("FEMALE");
        }
        if (role.equals("ADMIN")) {
            userDTO.setRole("ADMIN");
        }
        if (role.equals("CLIENT")) {
            userDTO.setRole("CLIENT");
        }
        if (role.equals("GUEST")) {
            userDTO.setRole("GUEST");
        }
        if (userStatus.equals("ACTIVE")) {
            userDTO.setUserStatus("ACTIVE");
        }
        if (userStatus.equals("BANNED")) {
            userDTO.setUserStatus("BANNED");
        }
        return userService.addUser(userDTO);
    }

}
