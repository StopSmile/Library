package com.example.Library.dto;

import com.example.Library.enums.BookStatus;
import com.example.Library.model.Language;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BookDTO {

    private long id;
    private String title;
    private String author;
    private int pages;
    private Language language;
    private BookStatus bookStatus;
    private String secret;

}
