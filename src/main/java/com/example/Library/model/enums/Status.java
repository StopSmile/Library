package com.example.Library.model.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statuses")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Status {
    @Id
    @GeneratedValue
    private int id;
    private String name;
}
