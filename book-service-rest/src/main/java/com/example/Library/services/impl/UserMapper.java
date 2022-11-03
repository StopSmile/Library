package com.example.Library.services.impl;

import com.example.Library.dto.UserDTO;
import com.example.Library.enums.Role;
import com.example.Library.enums.UserStatus;
import com.example.Library.model.Gender;
import com.example.Library.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        userDto.setGender(getGenderFromUser(user));
        userDto.setRole(getRoleFromUser(user));
        userDto.setUserStatus(getUserStatusFromUser(user));
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public String getGenderFromUser(User user) {
        if (user.getGender().getName().equals("MALE")) {
            return "MALE";
        }
        return "FEMALE";
    }

    public String getRoleFromUser(User user) {
        if (user.getRole().name().equals("ADMIN")) {
            return "ADMIN";
        }
        if (user.getRole().name().equals("CLIENT")) {
            return "CLIENT";
        }
        return "GUEST";
    }

    public String getUserStatusFromUser(User user) {
        if (user.getUserStatus().name().equals("ACTIVE")) {
            return "ACTIVE";
        }
        return "BANNED";
    }

    public User mapUserDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setGender(getGenderFromUserDto(userDTO));
        user.setRole(getRoleFromUserDto(userDTO));
        user.setUserStatus(getUserStatusFromUserDto(userDTO));
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public Gender getGenderFromUserDto(UserDTO userDTO) {
        if (userDTO.getGender().equals("MALE")) {
            return new Gender(1, "MALE");
        }
        return new Gender(2, "FEMALE");
    }

    public Role getRoleFromUserDto(UserDTO userDTO) {
        if (userDTO.getRole().equals("ADMIN")) {
            return Role.ADMIN;
        }
        if (userDTO.getRole().equals("CLIENT")) {
            return Role.CLIENT;
        }
        return Role.GUEST;
    }

    public UserStatus getUserStatusFromUserDto(UserDTO userDTO) {
        if (userDTO.getUserStatus().equals("ACTIVE")) {
            return UserStatus.ACTIVE;
        }
        return UserStatus.BANNED;
    }
}
