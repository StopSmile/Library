package com.example.Library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;
    private String gender;
    private String role;
    private String userStatus;

}
