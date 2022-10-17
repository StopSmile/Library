package com.example.Library.services.impl;

import com.example.Library.dto.UserDTO;
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
        userDto.setGender(user.getGender());
        userDto.setRole(user.getRole());
        userDto.setUserStatus(user.getUserStatus());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    public User mapUserDtoToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setRole(userDTO.getRole());
        user.setUserStatus(userDTO.getUserStatus());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}
