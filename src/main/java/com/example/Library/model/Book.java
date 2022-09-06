package com.example.Library.model;

import com.example.Library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String author;
    private int pages;
    @ManyToOne
    @JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "LANGUAGE_ID_FK")
    )
    private Language language;

    @Enumerated(value = EnumType.STRING)
    private BookStatus bookStatus;


}
