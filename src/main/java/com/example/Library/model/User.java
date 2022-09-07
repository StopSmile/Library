package com.example.Library.model;

import com.example.Library.enums.Role;
import com.example.Library.enums.UserStatus;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "gender_id", foreignKey = @ForeignKey(name = "GENDER_ID_FK")
    )
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;


}
