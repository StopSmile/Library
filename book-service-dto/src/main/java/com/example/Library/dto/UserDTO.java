package com.example.Library.dto;

import com.example.Library.model.Gender;
import com.example.Library.enums.Role;
import com.example.Library.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private Gender gender;
    private Role role;
    private UserStatus userStatus;

}
