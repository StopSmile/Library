package com.example.Library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genders")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Gender {
    @Id
    @GeneratedValue
    private int id;
    private String name;
}
