package com.example.Library.model;

import com.example.Library.model.enums.Gender;
import com.example.Library.model.enums.Role;
import com.example.Library.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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




//    @ElementCollection(targetClass = Role.class)
//    @CollectionTable(name = "person_role", joinColumns = @JoinColumn(name = "person_id"))
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role_name")
//    private Set<Role> skillSet;

}
