package org.example.librarymanagementservice.dto;

import lombok.*;
import org.example.librarymanagementservice.entities.Book;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;
    private String name;
    //private List<Book> books;

}
